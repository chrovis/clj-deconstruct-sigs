(ns clj-deconstruct-sigs.data
  (:require [clj-http.client :as http]
            [clojure.data.csv :as csv]
            [clojure.string :as string]
            [clojure.java.io :as io]))

(def cosmic-sigs-endpoint
  "https://cancer.sanger.ac.uk/cancergenome/assets/signatures_probabilities.txt")

(defn csv-blob []
  (-> (http/get cosmic-sigs-endpoint)
      :body
      (csv/read-csv :separator \tab)))

(defn trim-some-entries [csv-blob]
  (->> csv-blob (map (comp #(take 31 %) #(drop 2 %)))))

(defn matrix-with-headers [csv-blob]
  (trim-some-entries csv-blob))

(defn drop-headers [with-headers]
  (for [row  (->> with-headers
                  (map rest)
                  rest)]
    (for [cell row]
      (Double/parseDouble cell))))

(defn matrix-without-headers [matrix-with-headers]
  (drop-headers matrix-with-headers))

;;; Invoke (fetch-and-process) to fetch the latest signature from cosmic
(defn fetch-and-process []
  (let [csv (csv-blob)
        with-header (trim-some-entries csv)
        without-header (drop-headers with-header)]
    {:csv csv
     :with-header with-header
     :without-header without-header }
    (io/make-parents "src/clj_deconstruct_sigs/data/generated.clj")
    (spit "src/clj_deconstruct_sigs/data/generated.clj"
          (str (list 'ns 'clj-deconstruct-sigs.data.generated) "\n\n"
               (list 'def 'latest-cosmic-signatures (vec (map vec without-header)))))))
