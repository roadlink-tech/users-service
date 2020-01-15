package com.peya.usersservice.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY
import javax.validation.constraints.Email

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener::class)
data class User(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,
        var firstName: String = "",
        var lastName: String = "",
        @Email
        val email: String = "",
        @CreatedDate
        @Column(name = "created_date", nullable = false, updatable = false)
        var createdDate: LocalDateTime? = null,
        @LastModifiedDate
        @Column(name = "last_modified_date", nullable = false)
        var lastModifiedDate: LocalDateTime? = null
)
