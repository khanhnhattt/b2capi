# Use a base image with OpenJDK 17
FROM openjdk:17-jdk

# Copy the built JAR file from the builder stage to the container
COPY target/*.jar app.jar

# Expose the port your application is listening on
EXPOSE 8089

# Set the entrypoint command to run your application
CMD ["java", "-jar", "app.jar"]