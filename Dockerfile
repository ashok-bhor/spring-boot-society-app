# Use Maven image to build the application
FROM openjdk:8-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/spring-boot-society-app-0.0.1-SNAPSHOT.jar /app/spring-boot-society-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-society-app-0.0.1-SNAPSHOT.jar"]