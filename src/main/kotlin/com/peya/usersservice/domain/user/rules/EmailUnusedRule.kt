package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.exception.EmailAlreadyInUseException

class EmailUnusedRule(private val userRepository: UserRepository) : UserEvaluationRule {

    override fun evaluate(toEvaluate: User) {
        if (emailUsedByOtherUser(toEvaluate)) {
            throw EmailAlreadyInUseException("Email ${toEvaluate.email} is already taken by another account.")
        }
    }

    private fun emailUsedByOtherUser(user: User): Boolean {
        if (hasEmail(user)) {
            val existingUser = userRepository.findByEmail(user.email) ?: return false
            return areDistinct(existingUser, user)
        }
        return false
    }

    private fun areDistinct(existingUser: User, user: User): Boolean {
        return existingUser.id != user.id
    }

    private fun hasEmail(toEvaluate: User): Boolean {
        return !toEvaluate.email.isBlank()
    }
}