FROM ghcr.io/navikt/baseimages/temurin:17
ENV APPLICATION_NAME=familie-endringsmelding-api
EXPOSE 8000
COPY ./target/familie-endringsmelding-api-1.0-SNAPSHOT.jar "app.jar"
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
