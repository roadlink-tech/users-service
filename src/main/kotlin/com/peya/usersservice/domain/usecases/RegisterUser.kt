package com.peya.usersservice.domain.usecases

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.auth.AuthRepository
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserFactory

class RegisterUser(private val userFactory: UserFactory,
                   private val userRepository: UserRepository,
                   private val authRepository: AuthRepository) {

    fun execute(userDto: UserDto): User {
        val user = userFactory.create(userDto)
        userRepository.save(user)
        authRepository.saveAuthRegistrationInfo(userDto.password, user.email, user.phone)
        return user
    }
}