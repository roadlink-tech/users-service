package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import java.lang.RuntimeException
import java.util.regex.*

class EmailAddressPatternRule : UserEvaluationRule {

    private val pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})")

    override fun evaluate(toEvaluate: User) {
        if (!pattern.matcher(toEvaluate.email).matches()) {
            throw RuntimeException("Invalid email format")
        }
    }
}