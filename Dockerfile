# Use Java 17 (or 21 based on your project)
FROM openjdk:17-jdk-slim

# Copy jar file
COPY target/*.jar app.jar

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]