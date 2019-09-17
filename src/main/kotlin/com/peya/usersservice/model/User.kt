package com.peya.usersservice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
data class User(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,

        val firstName: String = "",

        val lastName: String = ""
)
