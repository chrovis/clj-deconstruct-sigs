(require '[ring.adapter.jetty :refer [run-jetty]])
(require '[clojure.java.shell :refer [sh]])
(require '[clojure.data.csv :as csv])
(require '[clojure.edn :as edn])


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


(def header
  (map
   #(str (:before %) "[" (:ref %) ">" (:alt %) "]" (:after %))
   trans-patterns))

(defn signature-count->csv-data [data]
  (let [counts (map #(get data % 0) trans-patterns)
        sum (apply + counts)]
    [header
     (map (comp double #(/ % sum)) counts)]))

(defn deconstruct-sigs [data]
  (prn "bbbbbb" data)
  (let [data (edn/read-string data)
        fname (or (str "csv/" (:file-name data))
                  (str "csv/" (java.util.UUID/randomUUID) ".csv"))]
    (with-open [w  (clojure.java.io/writer fname)]
      (csv/write-csv w
                     (-> data
                         :signature
                         signature-count->csv-data)))
    (->> (clojure.java.shell/sh "Rscript" "which-cosmic-signature.R" fname)
         :out
         csv/read-csv
         second
         (map-indexed (fn [idx v] {(inc idx) (Double/parseDouble v)}))
         (into {}))))

(run-jetty
 (comp
  (fn [in]
    (prn in)
    {:status 200
     :body (pr-str (deconstruct-sigs in))})
  slurp
  :body)
 {:port 8888
  :join? true})
