# Use official Eclipse Temurin (recommended)
FROM eclipse-temurin:17-jdk

# Copy jar file
COPY target/*.jar app.jar

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]