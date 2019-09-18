# source image
FROM maven:3.6.0-jdk-11-slim AS build
MAINTAINER Author Name "cabrerajjorge@gmail.com"

# copy pom.xml
COPY ./pom.xml ./pom.xml

# build all dependencies for offline use
RUN mvn dependency:go-offline -B

# copy your source tree
COPY ./src ./src

# build for release
RUN mvn package

# set the startup command to run your binary
ENTRYPOINT ["java", "-jar", "./target/users-service*.jar"]
EXPOSE 8080