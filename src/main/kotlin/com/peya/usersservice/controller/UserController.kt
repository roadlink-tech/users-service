package com.peya.usersservice.controller

import com.peya.usersservice.model.User
import com.peya.usersservice.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): User = userService.getUser(id)

}