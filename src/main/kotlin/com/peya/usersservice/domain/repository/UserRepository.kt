package com.peya.usersservice.domain.repository

import com.peya.usersservice.domain.entity.User
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : org.springframework.data.repository.Repository<User, Long> {

    @Query("select u from User u where u.id = :id and u.status != 'DELETED'")
    fun findById(id: Long): User?

    @Override
    fun save(user: User): User

    @Override
    fun deleteAll()

}