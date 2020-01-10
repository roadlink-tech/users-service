# User Service
[![GitHub Issues](https://img.shields.io/github/issues/jorgejcabrera/users-service)](https://github.com/jorgejcabrera/users-service/issues)

## Requirements
- [X] Kotlin - jdk11
- [X] Maven
- [X] Docker
- [X] MySql

## Developing application locally
First at all you must build user-service application with:
```
$ docker-compose build
```
and then
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
## Contributors
- Jorge Cabrera