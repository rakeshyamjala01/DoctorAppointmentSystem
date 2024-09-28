# Stage 1: Build the application with Maven
FROM maven:3.8.7-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files (pom.xml) and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the application code into the container
COPY src ./src

# Build the Spring Boot application and create the JAR file
RUN mvn clean package -DskipTests

# Use a base image with JDK 17 (adjust depending on your Spring Boot version)
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file built by Maven or Gradle into the container
COPY target/Eureka-Server-Main-0.0.1-SNAPSHOT.jar /app/Eureka-Server-Main-0.0.1-SNAPSHOT.jar

# Expose the port for Eureka (default: 8761)
EXPOSE 8761

# Command to run the Eureka Server
ENTRYPOINT ["java", "-jar", "/app/Eureka-Server-Main-0.0.1-SNAPSHOT.jar"]
