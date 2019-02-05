(ns clj-deconstruct-sigs.core-test
  (:require [clj-deconstruct-sigs.core :refer :all]
            [clojure.core.matrix :as m]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.test :refer :all]
            [clj-deconstruct-sigs.data.cosmic :as data-cosmic]
            [clj-deconstruct-sigs.dev.data :as dev-data]))

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

(deftest basic-which-signatures-test
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

(deftest cosmic-signatures-test
  (let [result (which-signatures
                (first random-tumor-samples)
                data-cosmic/cosmic-signatures)]
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

(deftest ^:slow compare-reference-result-test
  (let [answers (map #(-> (io/resource (str "answer/answer-" (inc %) ".csv"))
                          slurp
                          csv/read-csv
                          dev-data/drop-headers
                          first)
                     (range 100))
        my-answers (pmap #(to-vec (:weights (which-signatures % test-cosmic-sigatures)))
                         random-tumor-samples)]
    ;; For debugging as the computation in clojure takes a long time...
    (with-open [w (io/writer "/tmp/clj-output.csv")]
      (csv/write-csv w my-answers))
    (is (< (m/esum (map #(measure-difference %1 %2) answers my-answers))
           0.00001))))

(deftest signature->vector-test
  (is (= (signature->vector {}) nil))
  (is (= (signature->vector {{:ref \C :alt \G :before \T :after \A} 3})
         '(0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0))))
