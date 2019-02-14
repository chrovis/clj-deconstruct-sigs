(defproject clj-deconstruct-sigs "0.1.1-SNAPSHOT"
  :description "deconstructSigs for Clojure"
  :url "https://github.com/chrovis/clj-deconstruct-sigs"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies []
  :test-selectors {:default (complement :slow)
                   :slow :slow
                   :all (constantly true)}
  :profiles {:dev {:source-paths ["dev"]
                   :resource-paths ["dev-resources"]
                   :dependencies [[org.clojure/clojure "1.10.0"]
                                  [clj-http "3.9.1"]
                                  [org.clojure/data.csv "0.1.4"]
                                  [net.totakke/libra "0.1.1"]
                                  [criterium "0.4.4"]
                                  [net.mikera/vectorz-clj "0.48.0"]]
                   :global-vars {*warn-on-reflection* true
                                 *unchecked-math* :warn-on-boxed}
                   :plugins [[net.totakke/lein-libra "0.1.2"]]
                   :libra {:bench-paths ["bench"]}}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :1.10 {:dependencies [[org.clojure/clojure "1.10.0"]]}})
