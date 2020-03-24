package com.peya.usersservice.application.definition

import com.peya.usersservice.domain.auth.AuthRepository
import com.peya.usersservice.infrastructure.AuthRestClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AuthBeanDefinition {

    @Bean
    open fun authRepository(): AuthRepository {
        return AuthRestClient()
    }
}