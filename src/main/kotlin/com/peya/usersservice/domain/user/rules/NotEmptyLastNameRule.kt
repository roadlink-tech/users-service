package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.exception.EmptyDataException

class NotEmptyLastNameRule : UserEvaluationRule {

    override fun evaluate(toEvaluate: User) {
        if (toEvaluate.lastName.isEmpty()) {
            throw EmptyDataException("Last name must be not empty.")
        }

    }
}