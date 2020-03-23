package com.peya.usersservice.infrastructure

import com.peya.usersservice.domain.repository.AuthRepository

class AuthRestClient : AuthRepository {

    override fun saveAuthRegistrationInfo(pwd: String, email: String, phone: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}