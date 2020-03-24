package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User

class PhoneNumberPatternRule : UserEvaluationRule {

    private val pattern = "[+]{1}[0-9]{5,15}".toRegex()

    override fun evaluate(toEvaluate: User) {
        if (!pattern.matches(toEvaluate.phone)) {
            throw RuntimeException("Value $toEvaluate does not matches expected format")
        }
    }
}

