package com.peya.usersservice.domain.auth

interface AuthRepository {
    open fun saveAuthRegistrationInfo(pwd: String, email: String, phone: String)
}