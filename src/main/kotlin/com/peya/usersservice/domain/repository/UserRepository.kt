package com.peya.usersservice.domain.repository

import com.peya.usersservice.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>