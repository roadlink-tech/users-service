package com.peya.usersservice.domain.user

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.exception.EmailAlreadyInUseException
import com.peya.usersservice.domain.user.exception.EmptyDataException
import com.peya.usersservice.domain.user.exception.InvalidEmailAddressException
import com.peya.usersservice.domain.user.exception.InvalidPhoneNumberException
import com.peya.usersservice.domain.user.rules.EmailAddressPatternRule
import com.peya.usersservice.domain.user.rules.EmailUnusedRule
import com.peya.usersservice.domain.user.rules.NotEmptyFirstNameRule
import com.peya.usersservice.domain.user.rules.NotEmptyLastNameRule
import com.peya.usersservice.domain.user.rules.PhoneNumberPatternRule
import com.peya.usersservice.domain.user.rules.PhoneUnusedRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserFactoryTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var factory: UserFactory
    private val userWithInvalidName = UserDto(firstName = "", lastName = "cabrera", email = "cabrerajjorge@gmail.com", phone = "+5491154774264")
    private val userWithInvalidEmail = UserDto(firstName = "jorge", lastName = "cabrera", email = "cabrerajjorge", phone = "+5491154774264")
    private val userWithInvalidPhone = UserDto(firstName = "jorge", lastName = "cabrera", email = "cabrerajjorge@gmail.com", phone = "+")
    private val userWithWhitespaces = UserDto(firstName = "  jorge\t\n", lastName = "cabrera", email = "Cabrerajjorge@gmail.com", phone = "+5491154774264")
    private val userWithEmailAlreadyUsed = UserDto(firstName = "jorge", lastName = "cabreara", phone = "+5491169004107", email = "cabrerajjorge@gmail.com")
    private val expectedFirstNameNormalized = "jorge"
    private val expectedEmailNormalized = "cabrerajjorge@gmail.com"
    private val existingUser = User(id = 1L, email = expectedEmailNormalized, phone = "+5491169004107")


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        factory = UserFactory(listOf(
                EmailAddressPatternRule(),
                NotEmptyFirstNameRule(),
                NotEmptyLastNameRule(),
                PhoneNumberPatternRule(),
                EmailUnusedRule(userRepository),
                PhoneUnusedRule(userRepository)))
        `when`(userRepository.findByEmail(expectedEmailNormalized)).thenReturn(existingUser)
    }

    @Test
    fun `when try to create a user with an email already used by other user then should throw exception`() {
        assertThrows<EmailAlreadyInUseException> { factory.create(userWithEmailAlreadyUsed) }
    }

    @Test
    fun `when user data is invalid then should throw exception`() {
        assertAll("Invalid user data",
                { assertThrows<EmptyDataException> { factory.create(userWithInvalidName) } },
                { assertThrows<InvalidEmailAddressException> { factory.create(userWithInvalidEmail) } },
                { assertThrows<InvalidPhoneNumberException> { factory.create(userWithInvalidPhone) } }
        )
    }

    @Test
    fun `when user data has any whitespace then should be normalized`() {
        assertEquals(factory.create(userWithWhitespaces).firstName, expectedFirstNameNormalized)
        assertEquals(factory.create(userWithWhitespaces).email, expectedEmailNormalized)
    }
}