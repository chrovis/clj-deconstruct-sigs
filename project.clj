(defproject clj-deconstruct-sigs "0.1.0-SNAPSHOT"
  :description "deconstructSigs for Clojure"
  :url "https://github.com/chrovis/clj-deconstruct-sigs"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[net.mikera/vectorz-clj "0.48.0"]]
  :test-selectors {:default (complement :slow)
                   :slow :slow
                   :all (constantly true)}
  :profiles {:dev {:source-paths ["dev"]
                   :resource-paths ["dev-resource"]
                   :dependencies [[clj-http "3.9.1"]
                                  [org.clojure/data.csv "0.1.4"]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :1.10 {:dependencies [[org.clojure/clojure "1.10.0"]]}})
