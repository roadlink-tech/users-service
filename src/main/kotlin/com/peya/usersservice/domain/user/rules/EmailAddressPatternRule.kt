package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.exception.InvalidEmailAddressException
import java.util.regex.*

class EmailAddressPatternRule : UserEvaluationRule {

    private val pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})")

    override fun evaluate(toEvaluate: User) {
        if (!pattern.matcher(toEvaluate.email).matches()) {
            throw InvalidEmailAddressException("Invalid email format")
        }
    }
}