# Step 1: Build stage with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the JAR
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run stage with JDK
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the JAR built in the previous stage
COPY --from=build /app/target/project-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (Render uses this by default)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
