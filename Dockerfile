# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application's jar file to the container
COPY target/spring-boot-society-app-0.0.1-SNAPSHOT.jar /app/spring-boot-society-app-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","your-app.jar"]