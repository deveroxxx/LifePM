# Use OpenJDK 23 as base image
FROM eclipse-temurin:23-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/*.jar app.jar

# Define environment variables to override application.properties
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/life_pm
ENV SPRING_DATASOURCE_USERNAME=life_pm_admin
ENV SPRING_DATASOURCE_PASSWORD=dev_life_pm_admin
ENV SECURITY_JWT_SECRET_KEY=3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b
ENV FILES_ROOT_LOCATION="/app/storage"

# Expose the application port
EXPOSE 8081

# Run the application
CMD ["sh", "-c", "java -jar app.jar --server.port=8081"]