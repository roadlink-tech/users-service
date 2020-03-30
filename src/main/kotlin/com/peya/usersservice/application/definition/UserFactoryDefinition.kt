package com.peya.usersservice.application.definition

import com.peya.usersservice.domain.user.UserFactory
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.rules.EmailAddressPatternRule
import com.peya.usersservice.domain.user.rules.EmailUnusedRule
import com.peya.usersservice.domain.user.rules.NotEmptyFirstNameRule
import com.peya.usersservice.domain.user.rules.NotEmptyLastNameRule
import com.peya.usersservice.domain.user.rules.PhoneUnusedRule
import com.peya.usersservice.domain.user.rules.PhoneNumberPatternRule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UserFactoryDefinition {

    @Bean
    open fun userFactory(userRepository: UserRepository): UserFactory {
        return UserFactory(listOf(
                EmailAddressPatternRule(),
                NotEmptyFirstNameRule(),
                NotEmptyLastNameRule(),
                PhoneNumberPatternRule(),
                EmailUnusedRule(userRepository),
                PhoneUnusedRule(userRepository)))
    }
}