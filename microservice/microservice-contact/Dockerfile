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

WORKDIR /contact

COPY --from=build /app/target/*.jar /contact/microservice-contact-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","microservice-contact-0.0.1-SNAPSHOT.jar"]