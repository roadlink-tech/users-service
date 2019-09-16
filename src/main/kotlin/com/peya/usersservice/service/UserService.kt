package com.peya.usersservice.service

import com.peya.usersservice.model.User
import com.peya.usersservice.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()
}