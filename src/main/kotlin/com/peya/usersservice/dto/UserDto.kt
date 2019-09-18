package com.peya.usersservice.dto

import com.peya.usersservice.model.User
import javax.validation.constraints.Email

class UserDto(
        val firstName: String = "",

        val lastName: String = "",
        
        @Email
        val email: String = "") {

    fun toUser(): User = User(firstName = firstName, lastName = lastName, email = email)
}