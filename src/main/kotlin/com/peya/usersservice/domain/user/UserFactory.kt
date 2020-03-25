package com.peya.usersservice.domain.user

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.rules.UserEvaluationRule

class UserFactory(private val rules: List<UserEvaluationRule>) {

    fun create(userDto: UserDto): User {
        val user = fromUserDto(userDto)
        user.status = UserStatus.ACTIVE
        return user
    }

    private fun fromUserDto(userDto: UserDto): User {
        val user = userDto.toUser()
        evaluateRules(user)
        user.firstName = Normalizer.removeWhiteSpaces(userDto.firstName)
        user.lastName = Normalizer.removeWhiteSpaces(userDto.lastName)
        user.email = userDto.email.toLowerCase()
        return user
    }

    private fun evaluateRules(user: User) {
        rules.map { it.evaluate(user) }
    }
}