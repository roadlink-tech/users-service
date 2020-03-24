package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User

class NotEmptyLastNameRule : UserEvaluationRule {

    override fun evaluate(toEvaluate: User) {
        if (toEvaluate.lastName.isEmpty()) {
            throw RuntimeException("Last name must be not empty.")
        }

    }
}