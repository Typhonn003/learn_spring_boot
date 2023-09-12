#
# Build stage
#
FROM maven:3.9.4-eclipse-temurin AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:20
COPY --from=build /target/learning-spring-0.0.1-SNAPSHOT.jar learning_spring.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","learning_spring.jar"]