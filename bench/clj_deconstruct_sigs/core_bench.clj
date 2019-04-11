(ns clj-deconstruct-sigs.core-bench
  (:require [libra.bench :refer [defbench is]]
            [libra.criterium :refer [quick-bench]]
            [clj-deconstruct-sigs.core :as core]
            [clj-deconstruct-sigs.data.cosmic-test :as test-data]))

(defbench which-signatures-bench-1
  (let [t (first test-data/random-tumor-samples)]
    (is
     (quick-bench
      (core/which-signatures t test-data/test-cosmic-signatures)))))

(defbench which-signatures-bench-100
  (is
   (quick-bench
    (run!
     #(core/which-signatures % test-data/test-cosmic-signatures)
     test-data/random-tumor-samples))))
