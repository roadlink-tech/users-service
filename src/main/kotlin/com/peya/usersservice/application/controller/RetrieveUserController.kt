package com.peya.usersservice.application.controller

import com.peya.usersservice.domain.usecases.RetrieveUser
import com.peya.usersservice.domain.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val retrieveUserControllerUrl = "/user/{id}"

@RestController
@RequestMapping(retrieveUserControllerUrl)
class RetrieveUserController {

    @Autowired
    lateinit var action: RetrieveUser

    @GetMapping
    fun retrieveUser(@PathVariable("id") id: Long): User {
        return action.execute(id)
    }
}