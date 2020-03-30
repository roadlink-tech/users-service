package com.peya.usersservice.domain.usecases

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.UserFactory
import com.peya.usersservice.domain.user.exception.InvalidEmailAddressException
import com.peya.usersservice.domain.user.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class RegisterUserTest {

    @Mock
    private lateinit var userFactory: UserFactory
    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var registerUser: RegisterUser

    private val userWithValidData = UserDto(firstName = "jorge", lastName = "cabrera", email = "cabrerajjorge@gmail.com", phone = "+5491158125482")
    private val userWithInvalidEmail = UserDto(firstName = "jorge", lastName = "cabrera", email = "cabrerajjorge", phone = "+5491158125482")

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        registerUser = RegisterUser(userFactory, userRepository)
    }

    @Test
    fun `when create user with valid data then should work ok`() {
        //given
        saveUserFromValidData()

        //when
        registerUser.execute(userWithValidData)

        //then
        userWasCreated()
        userWasSaved()
    }

    @Test
    fun `when create user with invalid email then should throw exception`() {
        //given
        throwInvalidEmailAddressExceptionFromInvalidInput()

        //when
        Assertions.assertThrows(InvalidEmailAddressException::class.java) {
            registerUser.execute(userWithInvalidEmail)
        }
    }

    private fun throwInvalidEmailAddressExceptionFromInvalidInput() {
        `when`(userFactory.create(userWithInvalidEmail)).thenThrow(InvalidEmailAddressException::class.java)
    }

    private fun saveUserFromValidData() {
        `when`(userFactory.create(userWithValidData)).thenReturn(userWithValidData.toUser())
    }

    private fun userWasCreated() {
        verify(userFactory).create(userWithValidData)
    }

    private fun userWasSaved() {
        verify(userRepository).save(userWithValidData.toUser())
    }

}