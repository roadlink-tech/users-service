package com.peya.usersservice.domain.entity

import com.peya.usersservice.domain.enums.UserStatus
import com.peya.usersservice.domain.enums.UserStatus.DELETED
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table
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
        var email: String = "",
        @Enumerated(EnumType.STRING)
        var status: UserStatus? = null,
        @CreatedDate
        @Column(name = "created_date", nullable = false, updatable = false)
        var createdDate: LocalDateTime? = null,
        @LastModifiedDate
        @Column(name = "last_modified_date", nullable = false)
        var lastModifiedDate: LocalDateTime? = null
) {
    fun delete() {
        this.status = DELETED
    }
}
