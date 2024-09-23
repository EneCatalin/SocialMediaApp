# Use an official Maven image to build the application
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copy the entire project to the build container
COPY . .

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image for the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
