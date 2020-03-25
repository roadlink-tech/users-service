package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.exception.EmptyDataException

class NotEmptyFirstNameRule : UserEvaluationRule {

    override fun evaluate(toEvaluate: User) {
        if (toEvaluate.firstName.isEmpty()) {
            throw EmptyDataException("First name must be not empty.")
        }
    }
}