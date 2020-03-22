package com.peya.usersservice.application.controller

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.entity.User
import com.peya.usersservice.domain.service.UserService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Api(description = "General purpose operations")
@RestController
@RequestMapping(value = [UserController.path])
class UserController(private val userService: UserService) {

    companion object {
        const val path = "users"
    }

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

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    fun delete(@PathVariable("id") id: Long) {
        return userService.delete(id)
    }

}