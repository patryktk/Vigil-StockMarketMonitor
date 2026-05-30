#Stage 1 - build
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
COPY common/pom.xml common/pom.xml
COPY api-service/pom.xml api-service/pom.xml
COPY scraper-service/pom.xml scraper-service/pom.xml
COPY processor-service/pom.xml processor-service/pom.xml
COPY sheets-service/pom.xml sheets-service/pom.xml

RUN mvn dependency:go-offline -pl processor-service -am

COPY common/src common/src
COPY processor-service/src processor-service/src

RUN mvn clean package -pl processor-service -am -DskipTests

#Stage 2 - runtime
FROM eclipse-temurin:25-jre-alpine
RUN apk add --no-cache curl
LABEL org.opencontainers.image.authors="patryktk"
LABEL org.opencontainers.image.title="Processor serivce"
WORKDIR /app
COPY --from=build /app/processor-service/target/processor-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]