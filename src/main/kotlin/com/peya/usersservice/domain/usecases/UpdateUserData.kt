package com.peya.usersservice.domain.usecases

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.exception.UserNotFoundException
import com.peya.usersservice.domain.user.UserRepository

class UpdateUserData(private val userRepository: UserRepository) {

    fun execute(userDto: UserDto, id: Long): User {
        val user: User = userRepository.findById(id) ?: throw UserNotFoundException()
        user.firstName = userDto.firstName
        user.lastName = userDto.lastName
        user.email = userDto.email
        return userRepository.save(user)

    }
}