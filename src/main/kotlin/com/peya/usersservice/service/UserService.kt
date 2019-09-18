package com.peya.usersservice.service

import com.peya.usersservice.dto.UserDto
import com.peya.usersservice.exception.ResourceNotFound
import com.peya.usersservice.model.User
import com.peya.usersservice.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUser(id: Long): User {
        return userRepository.findById(id).orElseThrow { throw ResourceNotFound("User $id does not exists.") }
    }

    fun createUser(user: UserDto): User {
        return userRepository.save(user.toUser())
    }
}