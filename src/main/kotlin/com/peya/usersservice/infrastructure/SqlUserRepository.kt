package com.peya.usersservice.infrastructure

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserRepository

class SqlUserRepository(private val hibernateUserRepository: HibernateUserRepository) : UserRepository {

    override fun findById(id: Long): User? {
        return hibernateUserRepository.findById(id)
    }

    override fun save(user: User): User {
        return hibernateUserRepository.save(user)
    }

    override fun deleteAll() {
        return hibernateUserRepository.deleteAll()
    }

    override fun findByEmail(email: String): User? {
        return hibernateUserRepository.findByEmail(email)
    }

    override fun findByPhone(phone: String): User? {
        return hibernateUserRepository.findByPhone(phone)
    }
}