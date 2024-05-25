FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
# Ensure the Maven wrapper script is executable
RUN chmod +x ./mvnw

# Print the current directory, list files, and environment variables for debugging
RUN pwd && ls -al && env

# Use an absolute path for the Maven command to avoid any issues with relative paths
# RUN /app/mvnw -X clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# COPY --from=build /app/target/connection_checker-0.0.1-SNAPSHOT.jar connection_checker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "connection_checker-0.0.1-SNAPSHOT.jar"]
