package com.peya.usersservice.domain.repository

import com.peya.usersservice.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {


    fun find(id: Long): Optional<User> {
        val user = this.findById(id)
        if (user.isPresent && user.get().isNotDeleted()) {
            return user
        }
        return Optional.empty()
    }
}