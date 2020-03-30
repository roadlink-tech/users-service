package com.peya.usersservice.domain.user

interface UserRepository {
    fun findById(id: Long): User?
    fun save(user: User): User
    fun deleteAll()
    fun findByEmail(email: String): User?
    fun findByPhone(phone: String): User?
}