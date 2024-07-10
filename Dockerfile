# Use an official Maven runtime as a parent image with JDK 21
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project descriptor files (pom.xml) and the .mvn directory
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the entire project source and frontend directory
COPY src ./src

# Run the Vaadin frontend build and package the application
RUN mvn vaadin:prepare-frontend package -DskipTests

# Use the same JDK 21 image for the runtime stage
FROM eclipse-temurin:21

# Set the working directory in the container
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/weatherapp-1.0-SNAPSHOT.jar ./weatherapp.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "weatherapp.jar"]
