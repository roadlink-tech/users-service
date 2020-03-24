package com.peya.usersservice.domain.auth

interface AuthRepository {
    fun saveAuthRegistrationInfo(pwd: String, email: String, phone: String)
}