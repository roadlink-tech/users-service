package com.peya.usersservice.domain.service

import com.peya.usersservice.domain.dto.UserDto
import com.peya.usersservice.domain.exception.ResourceNotFound
import com.peya.usersservice.domain.entity.User
import com.peya.usersservice.domain.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    fun getUser(id: Long): User {
        logger.info("Retrieving user $id.")
        return userRepository.findById(id).orElseThrow { throw ResourceNotFound("User $id does not exists.") }
    }

    fun createUser(user: UserDto): User {
        return userRepository.save(user.toUser())
    }
}