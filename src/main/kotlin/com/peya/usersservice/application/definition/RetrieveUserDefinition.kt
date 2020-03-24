package com.peya.usersservice.application.definition

import com.peya.usersservice.domain.usecases.RetrieveUser
import com.peya.usersservice.domain.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RetrieveUserDefinition {

    @Bean
    open fun retrieveUser(userRepository: UserRepository): RetrieveUser {
        return RetrieveUser(userRepository)
    }
}