package com.peya.usersservice.application.dto

import com.peya.usersservice.domain.entity.User

data class UserDto(
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String
) {
    fun toUser(): User = User(firstName = firstName, lastName = lastName, email = email, phone = phone)

    override fun toString(): String {
        return "UserDto(firstName='$firstName', lastName='$lastName', email='$email', phone='$phone')"
    }
}