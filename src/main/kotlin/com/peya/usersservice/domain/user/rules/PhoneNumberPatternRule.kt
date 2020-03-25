package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.exception.InvalidPhoneNumberException

class PhoneNumberPatternRule : UserEvaluationRule {

    private val pattern = "[+]{1}[0-9]{5,15}".toRegex()

    override fun evaluate(toEvaluate: User) {
        if (!pattern.matches(toEvaluate.phone)) {
            throw InvalidPhoneNumberException("Invalid phone number")
        }
    }
}

