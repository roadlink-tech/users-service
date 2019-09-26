# User Service
[![GitHub Issues](https://img.shields.io/github/issues/jorgejcabrera/users-service)](https://github.com/jorgejcabrera/users-service/issues)

## Requirements
- [X] Kotlin - jdk11
- [X] Maven
- [X] Docker
- [X] MySql

## Developing application locally
```
$ docker-compose up
```
## Test your changes
Before pushing, make sure that all tests pass and there are no code style errors by running:
```
$ mvn test
```
## Architecture
![alt_text](https://github.com/jorgejcabrera/users-service/blob/master/img/architecture.jpg)
## Documentation
When server is running visit
```
http://localhost:8080/swagger-ui.html#/
```
## FAQ
If you are using IntelliJ IDEA make sure that:
- java compiler properties and project language level are ok. Have look at this [link](https://stackoverflow.com/questions/52340914/intellij-cant-find-java-net-http-when-compiling-with-java-11/52342080).  
- use the appropriate spring profile. For example you must set `spring.profiles.active=test` environment variable when you are running local test.
## Contributors
- Jorge Cabrera