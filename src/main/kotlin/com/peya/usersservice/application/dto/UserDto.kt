package com.peya.usersservice.application.dto

import com.peya.usersservice.domain.entity.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class UserDto(
        @field:NotNull @field:NotEmpty
        val firstName: String,
        val password: String = "",
        @field:NotNull @field:NotEmpty
        val lastName: String,
        @field:NotNull @field:NotEmpty @field:Email
        val email: String,
        @field:NotNull @field:NotEmpty @field:Pattern(regexp = "\\d+")
        val phone: String

) {
    fun toUser(): User = User(firstName = firstName, lastName = lastName, email = email, phone = phone)

    override fun toString(): String {
        return "UserDto(firstName='$firstName', lastName='$lastName', email='$email', phone='$phone')"
    }
}

/*
https://stackoverflow.com/questions/49896933/kotlin-data-class-and-bean-validation-notnull-on-long-fields-does-not-work
 */
