#Stage 1 - build
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
COPY eureka-server/pom.xml eureka-server/pom.xml

RUN mvn dependency:go-offline -pl eureka-server -am

COPY eureka-server/src eureka-server/src

RUN mvn clean package -pl eureka-server -am -DskipTests

#Stage 2 - runtime
FROM eclipse-temurin:25-jre-alpine
RUN apk add --no-cache curl
LABEL org.opencontainers.image.authors="patryktk"
LABEL org.opencontainers.image.title="Eureka server"
WORKDIR /app
COPY --from=build /app/eureka-server/target/eureka-server-*.jar app.jar

EXPOSE 8761

HEALTHCHECK --interval=10s --timeout=5s --start-period=20s --retries=5 \
    CMD curl -f http://localhost:8761/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]