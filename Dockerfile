#FROM ghcr.io/navikt/baseimages/temurin:17
FROM eclipse-temurin:17-jdk-alpine

ENV APPLICATION_NAME=familie-endringsmelding-api
ENV SPRING_PROFILES_ACTIVE=fly

EXPOSE 8000
COPY ./target/familie-endringsmelding-api-1.0-SNAPSHOT.jar "app.jar"

ENV JAVA_OPTS="-XX:MaxRAM=72m -XX:+UseSerialGC -Xshareclasses -Xquickstart"

ENTRYPOINT ["java", "-jar", "/app.jar"]