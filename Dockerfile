# Copied from: https://github.com/carlossg/docker-maven/blob/master/openjdk-17-slim/mvn-entrypoint.sh

FROM openjdk:17-jdk-slim AS builder

RUN apt-get update \
  && apt-get install -y curl procps \
  && rm -rf /var/lib/apt/lists/*

ARG MAVEN_VERSION=3.8.4
ARG USER_HOME_DIR="/root"
ARG SHA=a9b2d825eacf2e771ed5d6b0e01398589ac1bfa4171f36154d1b5787879605507802f699da6f7cfc80732a5282fd31b28e4cd6052338cbef0fa1358b48a5e3c8
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn


COPY . /usr/src/dogeared-extruder

WORKDIR /usr/src/dogeared-extruder

RUN mvn clean && mvn install

FROM openjdk:17-slim

FROM openjdk:17-slim

RUN mkdir /usr/local/jar

COPY --from=builder /usr/src/dogeared-extruder/target/extruder-2.0.jar /usr/local/jar/dogeared-extruder.jar
