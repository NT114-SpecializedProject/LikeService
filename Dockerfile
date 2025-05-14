# Build stage: Dùng Maven + Java 21 để build ứng dụng
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml và tải dependencies trước để tận dụng cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code vào container
COPY src ./src

# Build ứng dụng
RUN mvn clean package -DskipTests

# Run stage: Sử dụng JDK 21 nhẹ
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy JAR đã build từ stage trước
COPY --from=build /app/target/*.jar app.jar

# Mở cổng userservice chạy (ví dụ 8081)
EXPOSE 8083

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
