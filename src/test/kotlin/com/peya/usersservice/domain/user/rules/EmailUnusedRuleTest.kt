package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.exception.EmailAlreadyInUseException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EmailUnusedRuleTest {

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var emailUnusedRule: EmailUnusedRule
    private val existingUserEmail = "cabrerajjorge@gmail.com"
    private val newUserEmail = "jorgejcabrera@hotmail.com.ar"
    private val existingUser = User(id = 1L, email = existingUserEmail)
    private val userWithoutEmail = User()
    private val newUser = User(email = newUserEmail)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        emailUnusedRule = EmailUnusedRule(userRepository)
        `when`(userRepository.findByEmail(existingUserEmail)).thenReturn(existingUser)
    }

    @Test
    fun `when try to use an email already used by same user then should not throw exception`() {
        //when
        emailUnusedRule.evaluate(existingUser)

        //then
        noExceptionWasThrown()
    }

    @Test
    fun `when already exists a user with expected email then should throw exception`() {
        //given
        val user = User(email = existingUserEmail)

        //then
        assertThrows<EmailAlreadyInUseException> {
            emailUnusedRule.evaluate(user)
        }
    }

    @Test
    fun `when user does not have any email then should not throw exception`() {
        //when
        emailUnusedRule.evaluate(userWithoutEmail)

        //then
        noExceptionWasThrown()
    }

    @Test
    fun `when user has an unused email then should not throw exception`() {
        //when
        emailUnusedRule.evaluate(newUser)

        //then
        userEmailWasChecked(newUser.email)
        noExceptionWasThrown()
    }

    private fun userEmailWasChecked(email: String) {
        verify(userRepository).findByEmail(email)
    }

    private fun noExceptionWasThrown() {
        //nothing to do
    }
}