# source image
FROM maven:3.6.0-jdk-11-slim AS build
MAINTAINER Author Name "cabrerajjorge@gmail.com"

# copy pom.xml
COPY ./pom.xml ./pom.xml

# build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN mvn dependency:go-offline -B

# copy your source tree
COPY ./src ./src

# build for release and set the startup command to run your binary
RUN mvn package

ENTRYPOINT [ "sh", "-c", "java -jar -Dspring.profiles.active=prod -Duser.timezone=$TIMEZONE -XX:+UseG1GC -Xms256m -Xmx2048m -XX:PermSize=2048m -XX:MaxPermSize=2048m -Xss1m /target/*SNAPSHOT.jar" ]

EXPOSE 8080