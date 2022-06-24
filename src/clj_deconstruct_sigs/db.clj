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
    (let [rows (csv/read-csv rdr :separator \tab)
          emap (->> rows
                    (drop 1)
                    (map (fn [row]
                           [(first row) (rest row)]))
                    (into {}))]
      {:signature-names (rest (first rows))
       :signatures (->> trans-pattern-names
                        (map emap)          ; sort values by trans-pattern-names
                        (apply map vector)  ; transpose
                        (mapv #(mapv Double/parseDouble %))
                        doall)})))
