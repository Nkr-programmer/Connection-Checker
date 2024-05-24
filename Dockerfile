FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /connection_checker
COPY . .
# Ensure the Maven wrapper script is executable
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /connection_checker
COPY --from=build /app/target/connection_checker-0.0.1-SNAPSHOT.jar connection_checker.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","connection_checker.jar"]