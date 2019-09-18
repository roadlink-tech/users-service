package com.peya.usersservice.controller

import com.peya.usersservice.dto.UserDto
import com.peya.usersservice.model.User
import com.peya.usersservice.service.UserService
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "General purpose operations")
@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): User = userService.getUser(id)

    @PostMapping
    fun create(@Valid @RequestBody user: UserDto): User = userService.createUser(user)
}