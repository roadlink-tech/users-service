package com.peya.usersservice.application.definition

import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.infrastructure.HibernateUserRepository
import com.peya.usersservice.infrastructure.SqlUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserRepositoryDefinition {

    @Bean
    open fun userRepository(hibernateUserRepository: HibernateUserRepository): UserRepository {
        return SqlUserRepository(hibernateUserRepository)
    }
}