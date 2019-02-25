(ns clj-deconstruct-sigs.core-test
  (:require #?(:clj [clojure.test :refer :all]
               :cljs [cljs.test :refer-macros [deftest is testing]])
            [clj-deconstruct-sigs.core :as core]
            [clj-deconstruct-sigs.data.cosmic :as data-cosmic]
            [clj-deconstruct-sigs.data.cosmic-test :as test-data]))

(def ^:const ^:private which-signatures-keyset
  #{:seed-idx :weights :weights* :product :unknown :diff :error-sum})

(deftest basic-which-signatures-test
  (let [result (core/which-signatures
                (first test-data/random-tumor-samples)
                test-data/test-cosmic-signatures)]
    (is (= which-signatures-keyset (set (keys result))))))

(deftest cosmic-signatures-test
  (let [result (core/which-signatures
                (first test-data/random-tumor-samples)
                data-cosmic/cosmic-signatures)]
    (is (= which-signatures-keyset (set (keys result))))))

(defn- to-vec [m]
  (->> m (sort-by first) (map second)))

(defn- measure-difference [v1 v2]
  (Math/sqrt (reduce + 0.0 (map (comp #(Math/pow % 2.0) -) v1 v2))))

(deftest compare-reference-result-once-test
  (let [answer-weights (first test-data/answers)
        result (-> test-data/random-tumor-samples
                   first
                   (core/which-signatures test-data/test-cosmic-signatures))]
    (is (< (measure-difference answer-weights (to-vec (:weights result))) 1e-15))
    (is (= 25 (:seed-idx result)))
    (is (< (:error-sum result) 0.02369))))

(deftest ^:slow compare-reference-result-test
  (let [my-answers (#?(:clj pmap :cljs map)
                    #(-> %
                         (core/which-signatures test-data/test-cosmic-signatures)
                         :weights
                         to-vec)
                    test-data/random-tumor-samples)]
    (is (< (reduce + (map measure-difference test-data/answers my-answers)) 1e-5))))

(deftest ^:slow compare-reference-result-test-exome
  (let [my-answers (#?(:clj pmap :cljs map)
                    #(-> %
                         (core/which-signatures test-data/test-cosmic-signatures {:tri-count-method :exome})
                         :weights
                         to-vec)
                    test-data/random-tumor-samples)]
    (is (< (reduce + (map measure-difference test-data/answers-exome my-answers)) 1e-5))))

(deftest ^:slow compare-reference-result-test-genome
  (let [my-answers (#?(:clj pmap :cljs map)
                    #(-> %
                         (core/which-signatures test-data/test-cosmic-signatures
                                                {:tri-count-method :genome})
                         :weights
                         to-vec)
                    test-data/random-tumor-samples)]
    (is (< (reduce + (map measure-difference test-data/answers-genome my-answers)) 1e-5))))

(deftest ^:slow compare-reference-result-test-exome2genome
  (let [my-answers (#?(:clj pmap :cljs map)
                    #(-> %
                         (core/which-signatures test-data/test-cosmic-signatures
                                                {:tri-count-method :exome2genome})
                         :weights
                         to-vec)
                    test-data/random-tumor-samples)]
    (is (< (reduce + (map measure-difference test-data/answers-exome2genome my-answers)) 1e-5))))

(deftest ^:slow compare-reference-result-test-genome2exome
  (let [my-answers (#?(:clj pmap :cljs map)
                    #(-> %
                         (core/which-signatures test-data/test-cosmic-signatures
                                                {:tri-count-method :genome2exome})
                         :weights
                         to-vec)
                    test-data/random-tumor-samples)]
    (is (< (reduce + (map measure-difference test-data/answers-genome2exome my-answers)) 1e-5))))

(deftest signature->vector-test
  (is (= (core/signature->vector {}) nil))
  (is (= (core/signature->vector {{:ref \C :alt \G :before \T :after \A} 3})
         (-> (repeat 96 0.0) vec (assoc 28 1.0)))))
