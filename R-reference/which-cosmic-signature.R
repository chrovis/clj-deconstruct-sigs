library(deconstructSigs)
sigs = whichSignatures(tumor.ref = (read.csv(commandArgs(trailingOnly=TRUE)[1], check.names = FALSE)[1,]), signatures.ref = signatures.cosmic)[1]
write.csv(sigs, "", row.names = FALSE)
