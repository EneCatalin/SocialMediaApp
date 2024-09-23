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

# Install Git in the final image and verify the installation
RUN apt-get update && apt-get install -y git && git --version && rm -rf /var/lib/apt/lists/*

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
