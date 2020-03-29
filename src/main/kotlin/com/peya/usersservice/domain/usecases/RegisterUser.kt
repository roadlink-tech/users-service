package com.peya.usersservice.domain.usecases

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserFactory
import com.peya.usersservice.domain.user.UserRepository

class RegisterUser(private val userFactory: UserFactory,
                   private val userRepository: UserRepository) {

    fun execute(userDto: UserDto): User {
        val user = userFactory.create(userDto)
        userRepository.save(user)
        return user
    }
}