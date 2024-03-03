FROM openjdk:18-jdk-alpine as builder
WORKDIR /app/usermanagement_ms
COPY target/user_management_ms-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]