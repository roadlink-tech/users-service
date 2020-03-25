package com.peya.usersservice.domain.user.rules

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.exception.EmptyDataException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NotEmptyFirstNameRuleTest {

    lateinit var notEmptyFirstNameRule: NotEmptyFirstNameRule

    @BeforeEach
    fun setUp() {
        notEmptyFirstNameRule = NotEmptyFirstNameRule()
    }

    @Test
    fun `when user has empty name then should throw exception`() {
        //given
        val user = User(firstName = "")

        //then
        assertThrows<EmptyDataException> { notEmptyFirstNameRule.evaluate(user) }
    }
}