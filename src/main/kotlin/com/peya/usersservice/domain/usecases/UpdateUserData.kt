package com.peya.usersservice.domain.usecases

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserBuilder
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.exception.UserNotFoundException

class UpdateUserData(private val userRepository: UserRepository, private val userBuilder: UserBuilder) {

    fun execute(userData: UserDto, id: Long): User {
        val existingUser = userRepository.findById(id) ?: throw UserNotFoundException()
        val updatedUser = userBuilder
                .setId(existingUser.id)
                .setStatus(existingUser.status)
                .setUserData(userData)
                .build()
        return userRepository.save(updatedUser)
    }

}