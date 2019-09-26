package com.peya.usersservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class UsersServiceApplication

fun main(args: Array<String>) {
	runApplication<UsersServiceApplication>(*args)
}
