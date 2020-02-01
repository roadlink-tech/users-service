package com.peya.usersservice.domain.repository

import com.peya.usersservice.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :id and u.status = com.peya.usersservice.domain.enums.UserStatus.ACTIVE")
    fun findByIdOrNull(id: Long): User?
}
