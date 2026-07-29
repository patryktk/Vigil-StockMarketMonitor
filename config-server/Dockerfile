#Stage 1 - build
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
COPY config-server/pom.xml config-server/pom.xml

RUN mvn dependency:go-offline -pl config-server -am

COPY config-server/src config-server/src

RUN mvn clean package -pl config-server -am -DskipTests

#Stage 2 - runtime
FROM eclipse-temurin:25-jre-alpine
RUN apk add --no-cache curl
LABEL org.opencontainers.image.authors="patryktk"
LABEL org.opencontainers.image.title="Config server"
WORKDIR /app
COPY --from=build /app/config-server/target/config-server-*.jar app.jar

EXPOSE 8888

HEALTHCHECK --interval=10s --timeout=5s --start-period=20s --retries=5 \
    CMD curl -f http://localhost:8888/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]