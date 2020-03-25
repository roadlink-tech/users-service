package com.peya.usersservice.application.definition

import com.peya.usersservice.domain.usecases.UpdateUserData
import com.peya.usersservice.domain.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UpdateUserDefinition {

    @Bean
    open fun updateUserData(userRepository: UserRepository): UpdateUserData {
        return UpdateUserData(userRepository)
    }
}