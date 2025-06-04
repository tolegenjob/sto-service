FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/sto-service-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]