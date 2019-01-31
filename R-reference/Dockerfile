FROM bioconductor/release_base2

ENV CLOJURE_VERSION=1.9.0.397

COPY deps.edn /
COPY r-deps.R /
COPY which-cosmic-signature.R /
COPY run.sh /

RUN chmod +x run.sh

RUN apt-get update
RUN yes Y | apt-get upgrade
RUN yes Y | apt-get install openjdk-8-jdk curl

RUN wget https://download.clojure.org/install/linux-install-$CLOJURE_VERSION.sh \
    && chmod +x linux-install-$CLOJURE_VERSION.sh \
    && ./linux-install-$CLOJURE_VERSION.sh

RUN clojure -e "(clojure-version)"
RUN mkdir csv

RUN yes a | Rscript /r-deps.R --options --save

COPY serve.clj /