(ns clj-deconstruct-sigs.core)

#?(:clj (set! *unchecked-math* :warn-on-boxed))

(defn- l1-normalize
  "Returns a l1-normalized vector of `weights`."
  ^doubles [^doubles weights]
  (let [w-sum (double (areduce weights i ret 0.0 (+ ret (aget weights i))))]
    (amap weights i ret (/ (aget weights i) w-sum))))

#?(:clj (definline square [x] `(let [x# (double ~x)] (* x# x#)))
   :cljs (defn- square ^double [^double x] (* x x)))

(defn- sse
  "Computes sum of squared errors: |Aw - x|^2."
  ^double [^doubles x ^"[[D" A weights]
  (let [w (l1-normalize weights)]
    (areduce x j ret 0.0
             (let [a ^doubles (aget A j)]
               (+ ret (square (areduce w i e (- (aget x j))
                                       (+ e (* (aget a i) (aget w i))))))))))

(defn- euclidean-distance
  "Returns the euclidean distance between two vectors `x` and `y`."
  ^double [^doubles x ^doubles y]
  (Math/sqrt (areduce x i ret 0.0 (+ ret (square (- (aget x i) (aget y i)))))))

(defn- cut-off
  "Returns a copy of `array` whose elements less than `cutoff` are set to zero."
  ^doubles [^doubles array ^double cutoff]
  (amap array i ret (let [v (aget array i)] (if (< cutoff v) v 0.0))))

(defn- find-seed-idx
  "Returns an index of the signature nearest to `tumor`"
  ^long [tumor signatures]
  (->> signatures
       (map-indexed vector)
       (apply min-key (comp (partial euclidean-distance tumor) second))
       first))

(def ^:private ^:const golden-ratio (double (/ (dec (Math/sqrt 5.0)) 2.0)))

(defn- gold-search-min
  "Finds a scalar value between `lower` and `upper` giving the local minimum of
  the objective function `f` by golden section search."
  ^double [f ^double lower ^double upper]
  (let [eps 1e-6
        c (- upper (* (- upper lower) golden-ratio))
        d (+ lower (* (- upper lower) golden-ratio))]
    (loop [lower lower, upper upper, c c, d d]
      (if (> (Math/abs (- upper lower)) eps)
        (if (< (double (f c)) (double (f d)))
          (recur lower d (- d (* golden-ratio (- d lower))) c)
          (recur c upper d (+ c (* golden-ratio (- upper c)))))
        (/ (+ lower upper) 2.0)))))

(def ^:private ^:const aset-double' #?(:clj aset-double :cljs aset))

(defn- update-weights
  "Updates one element of weights to minimize the error."
  [tumor signatures ^doubles orig-w ^double orig-err]
  (let [n-sigs (alength orig-w)]
    (loop [i 0, min-err orig-err, min-w orig-w]
      (if (< i n-sigs)
        (let [w (aclone orig-w)
              err! (fn ^double [^double x]
                     (sse tumor signatures (doto w (aset-double' i x))))
              curr-x (gold-search-min err! 0.0 (+ 100.0 (aget orig-w i)))
              curr-err (double (err! curr-x))]
          (if (< curr-err min-err)
            (recur (inc i) curr-err w)
            (recur (inc i) min-err min-w)))
        [min-w min-err]))))

(defn- prune-signatures
  "Prunes signatures that are unlikely to affect results."
  [tumor signatures]
  (->> signatures
       (keep-indexed
        (fn [sig-idx sig]
          (when (->> (map vector tumor sig)
                     (every? (fn [[^double t ^double s]]
                               (or (<= 0.01 t) (<= s 0.2)))))
            [sig-idx sig])))))

(defn which-signatures
  "Finds a linear mixture of `signature-set` which best describes the given
  vector `sample-tumor`.

  Accepts the following options:
  :signature-cutoff - A threshold for cutting weights off.
                      Defaults to 0.06.
  :error-threshold  - Target error reduction rate to stop optimization.
                      Defaults to 1e-3.

  Returns a map with the following keys:
  :seed-idx  - Index of the signature that was used as the initial seed.
  :weights   - A map of weight indices to weight. :weights* with zero
               contribution entries.
  :weights*  - A map of non-zero weight indices to weight.
               Note that entries that have zero contribution will be missing.
  :product   - matrix product of :weights and the provided signatures.
  :unknown   - 1 minus the sum of weights.
  :diff      - Element wise difference of the provided sample tumor and the
               product.
  :error-sum - Square root of the sum of the element wise square of :diff.
               Indicates how close the :product is from the original input."
  ([sample-tumor signature-set]
   (which-signatures sample-tumor signature-set {}))
  ([sample-tumor signature-set
    {:keys [signature-cutoff ^double error-threshold]
     :or {signature-cutoff 0.06, error-threshold 1e-3}}]
   (let [[used-indices used-sigs] (->> signature-set
                                       (prune-signatures sample-tumor)
                                       (apply map vector))
         t (double-array sample-tumor)
         s (into-array (type t) (apply map (comp double-array vector) used-sigs))
         seed-idx (find-seed-idx t (map double-array used-sigs))
         w (loop [w' (doto (double-array (count used-sigs))
                       (aset-double' seed-idx 10.0))
                  prev-err (sse t s w')]
             (let [[new-w ^double new-err] (update-weights t s w' prev-err)]
               (if (< error-threshold (/ (- prev-err new-err) prev-err))
                 (recur new-w (double new-err))
                 (cut-off (l1-normalize new-w) signature-cutoff))))
         weights* (into {} (map vector used-indices w))
         weights (->> (range (count signature-set))
                      (map (fn [^long i] {(inc i) (get weights* i 0.0)}))
                      (into {}))
         product (apply mapv + (mapv #(mapv (partial * %1) %2) w used-sigs))
         unknown (areduce ^doubles w i u 1.0 (- u (aget ^doubles w i)))
         diff (mapv - sample-tumor product)
         error-sum (Math/sqrt (reduce + 0.0 (mapv square diff)))]
     {:seed-idx seed-idx, :weights weights, :weights* weights*,
      :product product, :unknown unknown, :diff diff, :error-sum error-sum})))

(def ^:private ^:const trans-patterns
  (for [[ref alts] [[\C "AGT"] [\T "ACG"]], alt alts, before "ACGT", after "ACGT"]
    {:ref ref, :alt alt, :before before, :after after}))

(defn signature->vector
  "Converts a frequencies map into a 96 element vector representing the
  substitution patterns."
  [data]
  (let [counts (map #(get data % 0) trans-patterns)
        sum (double (apply + counts))]
    (when (pos? sum)
      (mapv #(/ (double %) sum) counts))))
