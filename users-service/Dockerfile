# Use a specific Gradle image with Java 17
FROM gradle:7.5-jdk17 AS build
WORKDIR /app

# Copy the project files
COPY . .

# Download dependencies and build the project
RUN gradle clean build -x test

# Use an official OpenJDK runtime as a parent image for the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
