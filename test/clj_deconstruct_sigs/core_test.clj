(ns clj-deconstruct-sigs.core-test
  (:require [clojure.test :refer :all]
            [clj-deconstruct-sigs.core :as core]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clj-deconstruct-sigs.data.cosmic :as data-cosmic]
            [clj-deconstruct-sigs.dev.data :as dev-data]))

(def ^:const ^:private which-signatures-keyset
  #{:seed-idx :weights :weights* :product :unknown :diff :error-sum})

(def test-cosmic-signatures
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
  (let [result (core/which-signatures
                (first random-tumor-samples)
                test-cosmic-signatures)]
    (is (= which-signatures-keyset (set (keys result))))))

(deftest cosmic-signatures-test
  (let [result (core/which-signatures
                (first random-tumor-samples)
                data-cosmic/cosmic-signatures)]
    (is (= which-signatures-keyset (set (keys result))))))

(defn- to-vec [m]
  (->> m (sort-by first) (map second)))

(defn- measure-difference [v1 v2]
  (Math/sqrt (reduce + 0.0 (map (comp #(Math/pow % 2.0) -) v1 v2))))

(deftest compare-reference-result-once-test
  (let [answer-weights (-> "answer/answer-1.csv"
                           io/resource
                           slurp
                           csv/read-csv
                           dev-data/drop-headers
                           first)
        result (-> random-tumor-samples
                   first
                   (core/which-signatures test-cosmic-signatures))]
    (is (< (measure-difference answer-weights (to-vec (:weights result))) 1e-15))
    (is (= 25 (:seed-idx result)))
    (is (< (:error-sum result) 0.02369))))

(deftest ^:slow compare-reference-result-test
  (let [answers (map #(-> (io/resource (str "answer/answer-" (inc %) ".csv"))
                          slurp
                          csv/read-csv
                          dev-data/drop-headers
                          first)
                     (range 100))
        my-answers (pmap #(to-vec (:weights (core/which-signatures % test-cosmic-sigatures)))
                         random-tumor-samples)]
    (is (< (reduce + (map #(measure-difference %1 %2) answers my-answers)) 1e-5))))

(deftest signature->vector-test
  (is (= (core/signature->vector {}) nil))
  (is (= (core/signature->vector {{:ref \C :alt \G :before \T :after \A} 3})
         (-> (repeat 96 0.0) vec (assoc 28 1.0)))))
