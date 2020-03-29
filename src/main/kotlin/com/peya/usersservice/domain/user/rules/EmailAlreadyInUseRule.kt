package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.exception.EmailAlreadyInUseException

class EmailAlreadyInUseRule(private val userRepository: UserRepository) : UserEvaluationRule {

    override fun evaluate(toEvaluate: User) {
        if (hasEmail(toEvaluate) && userAlreadyExists(toEvaluate)) {
            throw EmailAlreadyInUseException("Email ${toEvaluate.email} is already taken by another account.")
        }
    }

    private fun hasEmail(toEvaluate: User): Boolean {
        return !toEvaluate.email.isBlank()
    }

    private fun userAlreadyExists(toEvaluate: User): Boolean {
        return userRepository.findByEmail(toEvaluate.email) != null
    }
}