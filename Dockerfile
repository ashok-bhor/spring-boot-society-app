FROM adoptopenjdk:8-jdk-hotspot AS build
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY . .
RUN mvn clean package

FROM adoptopenjdk:8-jdk-hotspot
WORKDIR /app
COPY --from=build /app/target/spring-boot-society-app-0.0.1-SNAPSHOT.jar /app/spring-boot-society-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-society-app-0.0.1-SNAPSHOT.jar"]
