# Use an official OpenJDK runtime as a base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/your-spring-app.jar /app/your-spring-app.jar

# Specify the default command to run when the container starts
CMD ["java", "-jar", "your-spring-app.jar"]