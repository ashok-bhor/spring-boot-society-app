# Stage 1: Build the application
FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw  # Add executable permissions to mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Create a minimal Java runtime environment
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/spring-boot-society-app-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
