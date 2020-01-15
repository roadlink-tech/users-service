package com.peya.usersservice.domain.service

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.entity.User
import com.peya.usersservice.domain.exception.ResourceNotFound
import com.peya.usersservice.domain.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    fun get(id: Long): User {
        return userRepository.findById(id).orElseThrow { throw ResourceNotFound("User $id does not exists.") }
    }

    fun create(user: UserDto): User {
        return userRepository.save(user.toUser())
    }

    fun update(id: Long, userDto: UserDto): User {
        val user: User = this.get(id)
        try {
            user.firstName = userDto.firstName
            user.lastName = userDto.lastName
            return userRepository.save(user)
        } catch (ex: Exception) {
            logger.error("It was an error while trying to update user ${user.id}. Error: ${ex.message}")
            throw ex
        }
    }
}