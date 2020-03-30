package com.peya.usersservice.domain.user

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.rules.UserEvaluationRule

class UserBuilder(private val rules: List<UserEvaluationRule>) {

    private var status: UserStatus? = null
    private lateinit var userDto: UserDto
    private var id: Long? = null

    fun setUserData(userDto: UserDto): UserBuilder {
        this.userDto = userDto
        return this
    }

    fun setId(id: Long): UserBuilder {
        this.id = id
        return this
    }

    fun setStatus(status: UserStatus?): UserBuilder {
        this.status = status
        return this
    }

    fun build(): User {
        val userData = userDto.toUser()
        userData.id = this.id!!
        userData.status = this.status
        userData.email = userData.email.toLowerCase()
        userData.phone = Normalizer.removeWhiteSpaces(userData.phone)
        userData.firstName = Normalizer.removeWhiteSpaces(userData.firstName)
        userData.lastName = Normalizer.removeWhiteSpaces(userData.lastName)
        evaluateRules(userData)
        return userData
    }

    private fun evaluateRules(user: User) {
        rules.map { it.evaluate(user) }
    }
}
