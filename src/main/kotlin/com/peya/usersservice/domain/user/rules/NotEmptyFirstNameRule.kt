package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User

class NotEmptyFirstNameRule : UserEvaluationRule {

    override fun evaluate(toEvaluate: User) {
        if (toEvaluate.firstName.isEmpty()) {
            throw RuntimeException("First name must be not empty.")
        }
    }
}