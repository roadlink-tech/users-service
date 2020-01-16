package com.peya.usersservice.domain.builder

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.entity.User
import com.peya.usersservice.domain.enums.UserStatus

class UserBuilder {

    private var userDto: UserDto? = null

    fun withUserDto(userDto: UserDto): UserBuilder {
        this.userDto = userDto
        return this
    }

    fun build(): User {
        val user = userDto!!.toUser()
        user.status = UserStatus.ACTIVE
        return user
    }

}