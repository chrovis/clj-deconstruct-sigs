library(deconstructSigs)
dir.create("dev-resources/answer")
for (tumor_i in 1:nrow(randomly.generated.tumors)) {write.csv(whichSignatures(tumor.ref=randomly.generated.tumors[tumor_i,], signatures.ref=signatures.cosmic)[1], paste("dev-resources/answer/answer-", tumor_i, ".csv" ,sep=""))}

for (tumor_i in 1:nrow(randomly.generated.tumors)) {write.csv(whichSignatures(tumor.ref=randomly.generated.tumors[tumor_i,], signatures.ref=signatures.cosmic, tri.counts.method='exome', contexts.needed=TRUE)[1], paste("dev-resources/answer/answer-exome-", tumor_i, ".csv" ,sep=""))}

for (tumor_i in 1:nrow(randomly.generated.tumors)) {write.csv(whichSignatures(tumor.ref=randomly.generated.tumors[tumor_i,], signatures.ref=signatures.cosmic, tri.counts.method='genome', contexts.needed=TRUE)[1], paste("dev-resources/answer/answer-genome-", tumor_i, ".csv" ,sep=""))}

for (tumor_i in 1:nrow(randomly.generated.tumors)) {write.csv(whichSignatures(tumor.ref=randomly.generated.tumors[tumor_i,], signatures.ref=signatures.cosmic, tri.counts.method='exome2genome', contexts.needed=TRUE)[1], paste("dev-resources/answer/answer-exome2genome-", tumor_i, ".csv" ,sep=""))}

for (tumor_i in 1:nrow(randomly.generated.tumors)) {write.csv(whichSignatures(tumor.ref=randomly.generated.tumors[tumor_i,], signatures.ref=signatures.cosmic, tri.counts.method='genome2exome', contexts.needed=TRUE)[1], paste("dev-resources/answer/answer-genome2exome-", tumor_i, ".csv" ,sep=""))}
