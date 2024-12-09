FROM openjdk:11 AS build

WORKDIR /app

COPY pom.xml mvnw ./
RUN chmod +x mvnw
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -Dmaven.test.skip

# For Java 11,
FROM eclipse-temurin:11-jre

WORKDIR /security

COPY --from=build /app/target/*.jar /security/microservice-security-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","microservice-security-0.0.1-SNAPSHOT.jar"]