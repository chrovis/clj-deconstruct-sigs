library(deconstructSigs)
dir.create("Routput")
for (tumor_i in 1:nrow(randomly.generated.tumors)) {write.csv(whichSignatures(tumor.ref=randomly.generated.tumors[tumor_i,], signatures.ref=signatures.cosmic)[1], paste("Routput/answer-", tumor_i, ".csv" ,sep=""))}
