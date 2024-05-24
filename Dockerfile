FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN ./mvnw clean package

FROM openjdk:17.0.1-jdk-slim
COPY --from=build target/connection_checker-0.0.1-SNAPSHOT.jar connection_checker.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","connection_checker.jar" ]