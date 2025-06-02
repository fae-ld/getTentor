# Stage 1: build
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: run
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/getTentor-*.jar app.jar
RUN mkdir -p /app/uploads
COPY uploads /app/uploads
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
