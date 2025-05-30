# OpenJDK 21 w Ubuntu (jammy) biar lebih stabil
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy JAR hasil build Maven ke container
COPY target/getTentor-*.jar app.jar

# Buat folder uploads supaya volume mount aman
RUN mkdir -p /app/uploads

# Expose port aplikasi
EXPOSE 8080

# Jalankan Spring Boot JAR dengan config external
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.config.location=file:/config/application.properties"]