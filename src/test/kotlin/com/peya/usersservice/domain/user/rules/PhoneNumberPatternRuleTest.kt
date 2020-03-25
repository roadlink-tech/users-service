package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.exception.InvalidPhoneNumberException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PhoneNumberPatternRuleTest {

    private lateinit var phoneNumberPatternRule: PhoneNumberPatternRule

    @BeforeEach
    fun setUp() {
        phoneNumberPatternRule = PhoneNumberPatternRule()
    }

    @Test
    fun `when phone number is invalid then should throw exception`() {
        //given
        val user = User(phone = "156900")

        //then
        assertThrows<InvalidPhoneNumberException> { phoneNumberPatternRule.evaluate(user) }
    }

    @Test
    fun `when phone number is valid then should work ok`() {
        //given
        val user = User(phone = "+5491169004107")

        //when
        phoneNumberPatternRule.evaluate(user)

        //then
        noExceptionWasThrown()
    }

    private fun noExceptionWasThrown() {
        //nothing to do
    }
}