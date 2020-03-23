package com.peya.usersservice.domain.repository

interface AuthRepository {
    fun saveAuthRegistrationInfo(pwd: String, email: String, phone: String)
}