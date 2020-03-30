package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.exception.PhoneAlreadyInUseException

class PhoneUnusedRule(private val userRepository: UserRepository) : UserEvaluationRule {

    override fun evaluate(toEvaluate: User) {
        if (phoneUsedByOtherUser(toEvaluate)) {
            throw PhoneAlreadyInUseException("Phone ${toEvaluate.phone} is already taken by another account.")
        }
    }

    private fun phoneUsedByOtherUser(user: User): Boolean {
        if (hasPhone(user)) {
            val existingUser = userRepository.findByPhone(user.phone) ?: return false
            return areDistinct(existingUser, user)
        }
        return false
    }

    private fun areDistinct(existingUser: User, user: User): Boolean {
        return existingUser.id != user.id
    }

    private fun hasPhone(toEvaluate: User): Boolean {
        return !toEvaluate.phone.isBlank()
    }
}