# Example of custom Java runtime using jlink in a multi-stage container build
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY . .
RUN ./gradlew bootJar

# Define your base image
FROM eclipse-temurin:21-jre-alpine AS production

COPY --from=build /app/build/libs/*.jar /app/la.jar

CMD ["java", "-jar", "/app/la.jar"]

