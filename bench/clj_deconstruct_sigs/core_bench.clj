(ns clj-deconstruct-sigs.core-bench
  (:require [libra.bench :refer [defbench is]]
            [libra.criterium :refer [quick-bench]]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clj-deconstruct-sigs.core :as core]
            [clj-deconstruct-sigs.dev.data :as dev-data]))

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

(defbench which-signatures-bench-1
  (let [t (first random-tumor-samples)]
    (is
     (quick-bench
      (core/which-signatures t test-cosmic-signatures)))))

(defbench which-signatures-bench-100
  (is
   (quick-bench
    (run!
     #(core/which-signatures % test-cosmic-signatures)
     random-tumor-samples))))
