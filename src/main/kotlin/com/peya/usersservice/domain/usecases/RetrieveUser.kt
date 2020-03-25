package com.peya.usersservice.domain.usecases

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.exception.UserNotFoundException
import com.peya.usersservice.domain.user.UserRepository

class RetrieveUser(private val userRepository: UserRepository) {

    fun execute(id: Long): User {
        return userRepository.findById(id) ?: throw UserNotFoundException()
    }
}