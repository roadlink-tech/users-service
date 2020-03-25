package com.peya.usersservice.application.definition

import com.peya.usersservice.domain.auth.AuthRepository
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.usecases.RegisterUser
import com.peya.usersservice.domain.user.UserFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RegisterUserDefinition {

    @Bean
    open fun registerUser(
            userFactory: UserFactory,
            userRepository: UserRepository,
            authRepository: AuthRepository): RegisterUser {
        return RegisterUser(userFactory, userRepository, authRepository)
    }
}