package com.peya.usersservice.application.controller

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.usecases.RegisterUser
import com.peya.usersservice.domain.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val registerUserControllerUrl = "/users"

@RestController
@RequestMapping(registerUserControllerUrl)
class RegisterUserController {

    @Autowired
    lateinit var action: RegisterUser

    @PostMapping
    fun register(@RequestBody body: UserDto): User {
        return action.execute(body)
    }

}