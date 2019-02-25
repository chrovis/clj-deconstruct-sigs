[![Clojars Project](https://img.shields.io/clojars/v/clj-deconstruct-sigs.svg)](https://clojars.org/clj-deconstruct-sigs)

[![Build Status](https://travis-ci.org/chrovis/clj-deconstruct-sigs.svg?branch=master)](https://travis-ci.org/chrovis/clj-deconstruct-sigs)

# clj-deconstruct-sigs

![Mutational Signature](https://cancer.sanger.ac.uk/signatures/Signature-1.png "Mutational Signature")

clj-deconstruct-sigs is a Clojure port of [deconstructSigs](https://github.com/raerose01/deconstructSigs).

## What are Mutational Signatures?

A comprehensive description can be found [here](https://cancer.sanger.ac.uk/cosmic/signatures).
In short, treating an individual's somatic mutation pattern as a combination of known distinct signatures could lead to insights of cancer's characteristics.

## Usage

The main function is `clj-deconstruct-sigs.core/which-signatures`, which takes a sample tumor and a reference signatures matrix and returns a map with the following keys.

```
:seed-idx - Index of the signature that was used as the initial seed
:weights  - A map of weight indices to weight. :weights* with zero contribution entries
:weights* - A map of non-zero weight indices to weight. Note that entries that have zero contribution will be missing.
:product - matrix product of :weights and the provided signatures
:unknown - 1 minus the sum of weights
:diff - element wise difference of the provided sample tumor and the product
:error-sum - Square root of the sum of the element wise square of :diff. Indicates how close the :product is from the original input
```

Here is a repl session that adds more context.

```clojure
(require '[clj-deconstruct-sigs.core :refer :all])

;; A 96 element vector representing the substitution patterns as per https://cancer.sanger.ac.uk/cosmic/signatures.
;; Start from A[C>A]A and ends with T[T>G]T
(def sample-tumor [0.00131420164040066,....])

;; Reference set of signatures to use, represented with m rows x 96 columns.
;; This library provides cosmic.signature-data.generated/latest-cosmic-signatures which is based on parsing the latest
;; signatures at https://cancer.sanger.ac.uk/cancergenome/assets/signatures_probabilities.txt
(require '[clj-deconstruct-sigs.data.cosmic])

(which-signatures sample-tumor clj-deconstruct-sigs.data.cosmic/cosmic-signatures)
=>
{:seed-idx 25,
 :weights {7 0.36016636298023036,...},
 :weights* {0 0.0, ...},
 :product [6.690145741717268E-4,...],
 :unknown 0.09329024976612943,
 :diff [6.451870662289333E-4,...],
 :error-sum 0.023682681269721964}
```

### Test data maintenance

Assuming deconstructSigs is installed, run `Rscript R-reference/R_answers.R `.

## License

Copyright 2019 [Xcoo, Inc.](https://xcoo.jp/)

Licensed under the [Apache License, Version 2.0](LICENSE).
