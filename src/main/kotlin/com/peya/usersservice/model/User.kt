package com.peya.usersservice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class User(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,

        @get: NotBlank
        val firstName: String = "",

        @get: NotBlank
        val lastName: String = ""
)
