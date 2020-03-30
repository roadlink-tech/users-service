package com.peya.usersservice.infrastructure

import com.peya.usersservice.domain.user.User
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface HibernateUserRepository : org.springframework.data.repository.Repository<User, Long> {

    @Query("select u from User u where u.id = :id and u.status != 'DELETED'")
    fun findById(id: Long): User?

    @Override
    fun save(user: User): User

    @Override
    fun deleteAll()

    @Override
    fun findByEmail(email: String): User?

    @Override
    fun findByPhone(phone: String): User?

}