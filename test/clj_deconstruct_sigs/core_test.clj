(ns clj-deconstruct-sigs.core-test
  (:require [clj-deconstruct-sigs.core :refer :all]
            [clojure.core.matrix :as m]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.test :refer :all]
            [clj-deconstruct-sigs.data.cosmic :as data-cosmic]
            [clj-deconstruct-sigs.dev.data :as dev-data]
            [clj-deconstruct-sigs.data.generated :refer [latest-cosmic-signatures latest-cosmic-signatures-transposed]]))

(deftest ensure-generated-data-exists
  (is latest-cosmic-signatures)
  (is latest-cosmic-signatures-transposed))

(def test-cosmic-sigatures
  (-> (io/resource "test-cosmic-signature.csv")
      slurp
      csv/read-csv
      dev-data/drop-headers))

(def random-tumor-samples
  (-> (io/resource "random-tumor-samples.csv")
      slurp
      csv/read-csv
      dev-data/drop-headers))

(deftest basic-smoke-test
  (let [result (which-signatures
                (first random-tumor-samples)
                test-cosmic-sigatures)]
    (is (= (set (keys result))
           #{:seed-idx
             :weights
             :weights*
             :product
             :unknown
             :diff
             :error-sum}))))

(deftest latest-cosmic-signatures-test
  (let [result (which-signatures
                (first random-tumor-samples)
                data-cosmic/cosmic-signatures-matrix)]
    (is (= (set (keys result))
           #{:seed-idx
             :weights
             :weights*
             :product
             :unknown
             :diff
             :error-sum}))))

(defn to-vec [m]
  (->>  m
        (sort-by first)
        (map second)))

(defn measure-difference [v1 v2]
  (Math/sqrt (m/esum (m/pow (m/sub v1 v2) 2))))

(deftest ^:slow compare-against-R-result-test
  (let [R-answers (map #(-> (slurp (str "Routput/answer-" (inc %) ".csv"))
                            csv/read-csv
                            dev-data/drop-headers
                            first)
                       (range 100))
        clj-answers (pmap #(to-vec (:weights (which-signatures % test-cosmic-sigatures)))
                          random-tumor-samples)]
    ;; For debugging as the computation in clojure takes a long time...
    (with-open [w (io/writer "clj-output.csv")]
      (csv/write-csv w clj-answers))
    (is (< (m/esum (map #(measure-difference %1 %2) R-answers clj-answers))
           0.00001))))
