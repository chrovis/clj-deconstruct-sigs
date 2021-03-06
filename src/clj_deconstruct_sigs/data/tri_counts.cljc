(ns clj-deconstruct-sigs.data.tri-counts)

;; Hardcoded exome and genome trinucleotide context counts based on hg19,
;; bundled together with the original deconstructSigs

(def exome
  {:ACA 1749730
   :ACC 1508395
   :ACG  547913
   :ACT 1401248
   :ATA 1037577
   :ATC 1274399
   :ATG 1642698
   :ATT 1433335
   :CCA 2322895
   :CCC 2085562
   :CCG  913559
   :CCT 2286274
   :CTA  846297
   :CTC 2067220
   :CTG 2788113
   :CTT 2003232
   :GCA 1835237
   :GCC 1986135
   :GCG  772325
   :GCT 1890595
   :GTA  961770
   :GTC 1255047
   :GTG 1762519
   :GTT 1226199
   :TCA 1877510
   :TCC 2021715
   :TCG  542569
   :TCT 2119281
   :TTA 1120093
   :TTC 1958262
   :TTG 1593181
   :TTT 2286246})

(def genome
  {:ACA 115415924
   :ACC  66550070
   :ACG  14381094
   :ACT  92058521
   :ATA 117976329
   :ATC  76401029
   :ATG 105094288
   :ATT 142651503
   :CCA 105547494
   :CCC  75238490
   :CCG  15801067
   :CCT 101628641
   :CTA  73791042
   :CTC  96335416
   :CTG 115950255
   :CTT 114180747
   :GCA  82414099
   :GCC  68090507
   :GCG  13621251
   :GCT  80004082
   :GTA  64915540
   :GTC  54055728
   :GTG  86012414
   :GTT  83421918
   :TCA 112085858
   :TCC  88336615
   :TCG  12630597
   :TCT 126566213
   :TTA 119020255
   :TTC 112827451
   :TTG 108406418
   :TTT 219915599})

(def exome2genome
  (merge-with (comp double /) genome exome))

(def genome2exome
  (merge-with (comp double /) exome genome))
