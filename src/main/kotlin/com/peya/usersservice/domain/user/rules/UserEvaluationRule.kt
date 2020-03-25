package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User

interface UserEvaluationRule {

    @Throws(RuntimeException::class)
    fun evaluate(toEvaluate: User)
}