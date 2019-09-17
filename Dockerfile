FROM maven:3.6.0-jdk-11-slim AS build

RUN mkdir /users-service
COPY . /users-service
WORKDIR /users-service
RUN mvn clean package
#ADD /target/*.jar users-service.jar
ENTRYPOINT ["java", "-jar", "users-service.jar"]
EXPOSE 8080