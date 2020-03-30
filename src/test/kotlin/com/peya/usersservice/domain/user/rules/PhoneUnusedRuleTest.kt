package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.exception.PhoneAlreadyInUseException
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PhoneUnusedRuleTest {

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var phoneUnusedRule: PhoneUnusedRule

    private val existingUserPhone = "+5491169004107"
    private val existingUser = User(phone = existingUserPhone)
    private val unusedUserPhone = "+5491159404201"
    private val newUser = User(phone = unusedUserPhone)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        phoneUnusedRule = PhoneUnusedRule(userRepository)
        Mockito.`when`(userRepository.findByPhone(existingUserPhone)).thenReturn(existingUser)
    }

    @Test
    fun `when phone is already used by other user then should throw exception`() {
        assertThrows<PhoneAlreadyInUseException> {
            phoneUnusedRule.evaluate(existingUser)
        }
    }

    @Test
    fun `when phone number has not been used then should pass the user rule`() {

        //when
        phoneUnusedRule.evaluate(newUser)

        //then
        phoneWasChecked(newUser.phone)
        noExceptionWasThrown()
    }

    private fun phoneWasChecked(phone: String) {
        Mockito.verify(userRepository).findByPhone(phone)
    }

    private fun noExceptionWasThrown() {
        //nothing to do
    }
}