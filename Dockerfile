FROM openjdk:8-jdk-alpine AS build
RUN apk add --no-cache maven
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/spring-boot-society-app-0.0.1-SNAPSHOT.jar /app/spring-boot-society-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-society-app-0.0.1-SNAPSHOT.jar"]
