package com.peya.usersservice.application.controller

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.entity.User
import com.peya.usersservice.domain.service.UserService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "General purpose operations")
@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    fun get(@PathVariable("id") id: Long): User {
        return userService.get(id)
    }

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@Valid @RequestBody user: UserDto): User {
        return userService.create(user)
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    fun update(@Valid @RequestBody user: UserDto, @PathVariable("id") id: Long): User {
        return userService.update(id, user)
    }

}