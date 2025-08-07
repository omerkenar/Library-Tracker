# Build stage
FROM maven:3.9.8-openjdk-21 AS build
WORKDIR /app

# For maven cache, coppy pom and src
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:21-jdk-slim
WORKDIR /app

RUN apk add --no-cache tzdata && cp /usr/share/zoneinfo/Europe/Istanbul /etc/localtime && echo "Europe/Istanbul" > /etc/timezone

# Coppy Jar
COPY --from=build /app/target/*.jar app.jar

# Spring profile and Java opts
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS=""

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
