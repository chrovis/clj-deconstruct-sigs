(ns clj-deconstruct-sigs.test-runner
  (:require  [doo.runner :refer-macros [doo-tests]]
             [clj-deconstruct-sigs.core-test]))

(doo-tests 'clj-deconstruct-sigs.core-test)

