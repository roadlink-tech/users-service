package com.peya.usersservice.domain.dto

import com.peya.usersservice.domain.entity.User
import javax.validation.constraints.Email

class UserDto(
        val firstName: String = "",

        val lastName: String = "",
        
        @Email
        val email: String = "") {

    fun toUser(): User = User(firstName = firstName, lastName = lastName, email = email)
}