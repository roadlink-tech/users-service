package com.peya.usersservice.application.dto

import com.peya.usersservice.domain.entity.User
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Email

data class UserDto(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "") {

    fun toUser(): User = User(firstName = firstName, lastName = lastName, email = email)

    override fun toString(): String {
        return "UserDto(firstName='$firstName', lastName='$lastName', email='$email')"
    }
}