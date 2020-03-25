package com.peya.usersservice.domain.user

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.rules.EmailAddressPatternRule
import com.peya.usersservice.domain.user.rules.NotEmptyFirstNameRule
import com.peya.usersservice.domain.user.rules.NotEmptyLastNameRule
import com.peya.usersservice.domain.user.rules.PhoneNumberPatternRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class UserFactoryTest {

    lateinit var factory: UserFactory
    private val userWithInvalidName = UserDto(firstName = "", lastName = "cabrera", email = "cabrerajjorge@gmail.com", phone = "+5491154774264")
    private val userWithInvalidEmail = UserDto(firstName = "jorge", lastName = "cabrera", email = "cabrerajjorge", phone = "+5491154774264")
    private val userWithInvalidPhone = UserDto(firstName = "jorge", lastName = "cabrera", email = "cabrerajjorge@gmail.com", phone = "+")
    private val userWithWhitespaces = UserDto(firstName = "  jorge\t\n", lastName = "cabrera", email = "Cabrerajjorge@gmail.com", phone = "+5491154774264")

    private val expectedFirstNameNormalized = "jorge"
    private val expectedEmailNormalized = "cabrerajjorge@gmail.com"

    @BeforeEach
    fun setUp() {
        factory = UserFactory(listOf(
                EmailAddressPatternRule(),
                NotEmptyFirstNameRule(),
                NotEmptyLastNameRule(),
                PhoneNumberPatternRule()))
    }

    @Test
    fun `when user data is invalid then should throw exception`() {
        assertAll("Invalid user data",
                { assertThrows<RuntimeException> { factory.create(userWithInvalidName) } },
                { assertThrows<RuntimeException> { factory.create(userWithInvalidEmail) } },
                { assertThrows<RuntimeException> { factory.create(userWithInvalidPhone) } }
        )
    }

    @Test
    fun `when user data has any whitespace then should be normalized`() {
        assertEquals(factory.create(userWithWhitespaces).firstName, expectedFirstNameNormalized)
        assertEquals(factory.create(userWithWhitespaces).email, expectedEmailNormalized)
    }
}