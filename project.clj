(defproject clj-deconstruct-sigs "0.4.0"
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
                   :dependencies [[org.clojure/clojure "1.11.1"]
                                  [org.clojure/clojurescript "1.11.60"]
                                  [clj-http "3.12.3"]
                                  [org.clojure/data.csv "1.0.1"]
                                  [cider/piggieback "0.5.3"]
                                  [net.totakke/libra "0.1.1"]
                                  [criterium "0.4.6"]
                                  [fipp "0.6.26"]]
                   :global-vars {*warn-on-reflection* true
                                 *unchecked-math* :warn-on-boxed}
                   :plugins [[lein-cljsbuild "1.1.7"]
                             [lein-doo "0.1.11"]
                             [net.totakke/lein-libra "0.1.2"]
                             [lein-cljfmt "0.8.0"]]
                   :libra {:bench-paths ["bench"]}
                   :cljsbuild {:builds
                               {:test
                                {:source-paths ["src" "test"]
                                 :compiler {:output-to "target/main.js"
                                            :output-dir "target"
                                            :main clj-deconstruct-sigs.test-runner
                                            :optimizations :simple
                                            :target :nodejs}}}}
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]
                                  [org.clojure/clojurescript "1.9.946"]
                                  [javax.xml.bind/jaxb-api "2.4.0-b180830.0359"]]}
             :1.10 {:dependencies [[org.clojure/clojure "1.10.3"]
                                   [org.clojure/clojurescript "1.10.914"]]}
             :1.11 {:dependencies [[org.clojure/clojure "1.11.1"]
                                   [org.clojure/clojurescript "1.11.60"]]}})
