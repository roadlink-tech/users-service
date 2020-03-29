package com.peya.usersservice.application.definition

import com.peya.usersservice.domain.usecases.RegisterUser
import com.peya.usersservice.domain.user.UserFactory
import com.peya.usersservice.domain.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RegisterUserDefinition {

    @Bean
    open fun registerUser(
            userFactory: UserFactory,
            userRepository: UserRepository): RegisterUser {
        return RegisterUser(userFactory, userRepository)
    }
}