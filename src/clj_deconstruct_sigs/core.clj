(ns clj-deconstruct-sigs.core
  (:require [clojure.core.matrix :as m]))

(def acgt [\A \C \G \T])

(def ref-alt-patterns [{:ref \C :alt \A}
                       {:ref \C :alt \G}
                       {:ref \C :alt \T}
                       {:ref \T :alt \A}
                       {:ref \T :alt \C}
                       {:ref \T :alt \G}])

(def trans-patterns (->> ref-alt-patterns
                         (map (fn [p]
                                (for [before acgt after acgt]
                                  (assoc p :before before :after after))))
                         flatten))

(m/set-current-implementation :vectorz)

(defn normalize [w]
  (m/div w (m/esum w)))

(defn get-error [tumor signatures weights]
  (let [product (m/mmul (normalize weights) signatures)]
    (-> (m/sub tumor product)
        (m/pow 2)
        m/esum)))

(defn find-min-idx [v]
  (apply min-key second (map-indexed vector v)))

(defn find-seed [tumor signatures]
  (let [ ws (m/identity-matrix (m/row-count signatures))
        errors (map (fn [w] (get-error tumor signatures w)) ws)
        [min-err-idx] (find-min-idx errors)]
    min-err-idx))

(def golden-ratio (/ (dec (Math/sqrt 5)) 2))

(defn gold-search-min [f ^double lower ^double upper]
  (let [eps 1e-6
        c (- upper (* (- upper lower) golden-ratio))
        d (+ lower (* (- upper lower) golden-ratio))]
    (loop [lower lower
           upper upper
           c c
           d d]
      (if (> (Math/abs (- upper lower))
             eps)
        (let [fc (f c) fd (f d)]
          (if (< fc fd)
            (recur lower
                   d
                   (- d (* golden-ratio (- d lower)))
                   c)
            (recur c
                   upper
                   d
                   (+ c (* golden-ratio (- upper c))))))
        (/ (+ lower upper) 2)))))

(defn update-forward [tumor signatures w-orig v boo]
  (loop [i 0
         w w-orig
         v v
         boo boo]
    (if (< i (m/row-count signatures))
      (let [to-minimize (fn [x]
                          (get-error tumor signatures
                                     (m/add w-orig
                                            (-> [1 (m/row-count signatures)]
                                                m/zero-array
                                                (m/set-column i x)))))
            min-x (gold-search-min to-minimize
                                   (- (first (m/get-column w-orig i)))
                                   100)
            v-new (m/set-indices v [[i i]] [min-x])
            w-new (m/add w-orig (m/get-row v-new i))
            get-err-res (get-error tumor signatures w-new)]
        (recur (inc i)
               w-new
               v-new
               (m/set-column boo i get-err-res)))
      {:w w
       :v v
       :boo boo})))

(defn update-W-GR [tumor signatures w-orig]
  (let [err-old (get-error tumor signatures w-orig)
        ;;TODO: rename boo to ith-error vector
        boo (m/matrix [(repeat (m/row-count signatures) Double/POSITIVE_INFINITY)])
        v-init (m/zero-matrix (m/row-count signatures) (m/row-count signatures))
        {:as forward :keys [v boo]} (update-forward tumor signatures w-orig v-init boo)
        [min-err-idx min-err] (find-min-idx (first boo))
        w-new (m/add w-orig (m/get-row v min-err-idx))]
    (if (< min-err err-old)
      w-new
      w-orig)))

(defn prune-signatures [tumor signatures]
  (let [zero-corr-nucs (set (keep-indexed (fn [idx s] (when (< s 0.01) idx))
                                          tumor))]
    (keep-indexed (fn [sig-idx sig]
                    (if (->>  sig
                              (keep-indexed (fn [idx mut]
                                              (when (and (> mut 0.2)
                                                         (zero-corr-nucs idx))
                                                idx)))
                              seq)
                      nil
                      [sig-idx sig]))
                  (m/matrix signatures))))

(defn which-signatures [sample-tumor signature-set]
  (let [pruned (prune-signatures sample-tumor signature-set)
        signature-cutoff 0.06
        sigs (map second pruned)
        used-indices (map first pruned)
        seed-idx (find-seed sample-tumor sigs)
        w (loop [w (m/set-column (m/zero-array [1 (count sigs)]) seed-idx 10)
                 error-diff Double/POSITIVE_INFINITY]
            (if (< 1e-3 error-diff)
              (let [error-pre ^double (get-error sample-tumor sigs w)
                    new-w (update-W-GR sample-tumor sigs w)
                    error-post ^double (get-error sample-tumor sigs new-w)
                    next-error-diff ^double (double (/ (- error-pre error-post) error-pre))]
                (recur new-w next-error-diff))
              w))
        weights* (->> (map (fn [w orig-sig-idx] {orig-sig-idx w})
                           (m/emap #(if (< signature-cutoff %) % 0)
                                   (m/get-row (m/div w (m/esum w)) 0))
                           used-indices)
                      (into {}))
        weights-matrix (->> (range (count signature-set))
                            (map (fn [i] (get weights* i 0))))
        weights-map (->> (range (count signature-set))
                         (map (fn [i] {(inc i) (get weights* i 0)}))
                         (into {}))
        product (m/mmul weights-matrix signature-set)
        diff (m/sub sample-tumor product)]
    {:seed-idx seed-idx
     :weights weights-map
     :weights* weights*
     :product product
     :unknown (- 1 (m/esum weights-matrix))
     :diff diff
     :error-sum (Math/sqrt (m/esum (m/mul diff diff))) }))

(defn signature-count->matrix [data]
  (let [counts (map #(get data % 0) trans-patterns)
        sum (apply + counts)]
    (map (comp double #(/ % sum)) counts)))
