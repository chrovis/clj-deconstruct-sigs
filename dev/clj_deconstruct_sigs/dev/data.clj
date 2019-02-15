(ns clj-deconstruct-sigs.dev.data
  (:require [clj-http.client :as http]
            [clojure.data.csv :as csv]
            [clojure.string :as string]
            [clojure.java.io :as io]
            [fipp.clojure :as fipp]))

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


(defn write-test-data-cljc!
  "Converts CSV files containing test datasets into a cljc file."
  ([ns']
   (write-test-data-cljc! ns' {}))
  ([ns' opts]
   (let [test-cosmic-signatures (->> (io/resource "test-cosmic-signature.csv")
                                     slurp
                                     csv/read-csv
                                     drop-headers
                                     (mapv vec))
         random-tumor-samples (->> (io/resource "random-tumor-samples.csv")
                                   slurp
                                   csv/read-csv
                                   drop-headers
                                   (mapv vec))
         answers (mapv #(-> (str "answer/answer-" (inc ^long %) ".csv")
                            io/resource
                            slurp
                            csv/read-csv
                            drop-headers
                            first
                            vec)
                       (range 100))
         f (-> ns'
               str
               (string/replace #"\-" "_")
               (string/replace #"\." "/")
               (as-> s (str "test/" s ".cljc")))]
     (with-open [w (io/writer f)]
       (binding [*out* w]
         (fipp/pprint `(~'ns ~ns'))
         (newline)
         (fipp/pprint `(def ~'test-cosmic-signatures ~test-cosmic-signatures) opts)
         (newline)
         (fipp/pprint `(def ~'random-tumor-samples ~random-tumor-samples) opts)
         (newline)
         (fipp/pprint `(def ~'answers ~answers) opts))))))


(comment

  (write-test-data-cljc! 'clj-deconstruct-sigs.data.cosmic-test {:width 5000})

  )
