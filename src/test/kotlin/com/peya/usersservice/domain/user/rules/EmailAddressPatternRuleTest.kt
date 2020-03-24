package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

object EmailAddressPatternRuleTest {

    private lateinit var addressPattern: EmailAddressPatternRule

    @BeforeEach
    fun setUp() {
        addressPattern = EmailAddressPatternRule()
    }

    @Test
    fun `when email has invalid format then should throw exception`() {
        assertAll("InvalidEmailFormat",
                { assertThrows<InvalidEmailAddressException> { addressPattern.evaluate(User(email = "cabrerajjorge@gmail")) } },
                { assertThrows<InvalidEmailAddressException> { addressPattern.evaluate(User(email = "cabrera.com")) } },
                { assertThrows<InvalidEmailAddressException> { addressPattern.evaluate(User(email = "jorge.cabrera@.com")) } },
                { assertThrows<InvalidEmailAddressException> { addressPattern.evaluate(User(email = "jorge.cabrera#123@.com")) } },
                { assertThrows<InvalidEmailAddressException> { addressPattern.evaluate(User(email = "jorge.cabrera@    .com")) } }
        )
    }

}
