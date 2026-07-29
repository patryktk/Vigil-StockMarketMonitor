#Stage 1 - build
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
COPY common/pom.xml common/pom.xml
COPY scraper-service/pom.xml scraper-service/pom.xml
COPY sheets-service/pom.xml sheets-service/pom.xml

RUN mvn dependency:go-offline -pl sheets-service -am

COPY common/src common/src
COPY sheets-service/src sheets-service/src

RUN mvn clean package -pl sheets-service -am -DskipTests

#Stage 2 - runtime
FROM eclipse-temurin:25-jre-alpine
RUN apk add --no-cache curl
LABEL org.opencontainers.image.authors="patryktk"
LABEL org.opencontainers.image.title="Sheets service"
WORKDIR /app
COPY --from=build /app/sheets-service/target/sheets-service-*.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=10s --timeout=5s --start-period=20s --retries=5 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]