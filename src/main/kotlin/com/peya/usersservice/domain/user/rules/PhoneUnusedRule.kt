package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.exception.PhoneAlreadyInUseException

class PhoneUnusedRule(private val userRepository: UserRepository) : UserEvaluationRule {

    override fun evaluate(toEvaluate: User) {
        if (hasPhone(toEvaluate) && userAlreadyExists(toEvaluate)) {
            throw PhoneAlreadyInUseException("Phone ${toEvaluate.phone} is already taken by another account.")
        }
    }

    private fun hasPhone(toEvaluate: User): Boolean {
        return !toEvaluate.phone.isBlank()
    }

    private fun userAlreadyExists(toEvaluate: User): Boolean {
        return userRepository.findByPhone(toEvaluate.phone) != null
    }
}