package com.peya.usersservice.application.controller

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.usecases.UpdateUserData
import com.peya.usersservice.domain.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

const val updateUserDataControllerUrl = "/user/{id}"

@RestController
@RequestMapping(updateUserDataControllerUrl)
class UpdateUserDataController {

    @Autowired
    lateinit var action: UpdateUserData

    @PutMapping
    fun updateUser(@Valid @RequestBody user: UserDto, @PathVariable("id") id: Long): User {
        return action.execute(user, id)
    }

}