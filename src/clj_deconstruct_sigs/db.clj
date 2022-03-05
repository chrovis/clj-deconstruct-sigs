(ns clj-deconstruct-sigs.db
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.data.csv :as csv]))

(def ^:private trans-patterns
  (vec
   (for [[ref alts] [[\C "AGT"] [\T "ACG"]], alt alts, before "ACGT", after "ACGT"]
     {:ref ref, :alt alt, :before before, :after after})))

(def ^:private trans-pattern-names
  (mapv #(str (:before %)
              "["
              (:ref %)
              ">"
              (:alt %)
              "]"
              (:after %))
        trans-patterns))

(defn load-signature-database
  [f]
  (with-open [rdr (io/reader f)]
    (let [emap (->> (line-seq rdr)
                    (map #(string/split % #"\t"))
                    (drop 1)
                    (map (fn [row]
                           [(first row) (rest row)]))
                    (into {}))]
      (->> trans-pattern-names
           (map emap)
           (apply map vector)
           (mapv (fn [xs]
                   (mapv #(Double/parseDouble %) xs)))
           doall))))
