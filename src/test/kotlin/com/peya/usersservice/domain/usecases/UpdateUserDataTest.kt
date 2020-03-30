package com.peya.usersservice.domain.usecases

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserBuilder
import com.peya.usersservice.domain.user.UserRepository
import com.peya.usersservice.domain.user.UserStatus.ACTIVE
import com.peya.usersservice.domain.user.exception.EmailAlreadyInUseException
import com.peya.usersservice.domain.user.exception.InvalidEmailAddressException
import com.peya.usersservice.domain.user.exception.PhoneAlreadyInUseException
import com.peya.usersservice.domain.user.exception.UserNotFoundException
import com.peya.usersservice.domain.user.rules.EmailAddressPatternRule
import com.peya.usersservice.domain.user.rules.EmailUnusedRule
import com.peya.usersservice.domain.user.rules.NotEmptyFirstNameRule
import com.peya.usersservice.domain.user.rules.NotEmptyLastNameRule
import com.peya.usersservice.domain.user.rules.PhoneNumberPatternRule
import com.peya.usersservice.domain.user.rules.PhoneUnusedRule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class UpdateUserDataTest {

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var userFactory: UserBuilder
    private lateinit var updateUserData: UpdateUserData

    private val userIdToUpdate = 100L
    private val unExistingUserId = 1000L
    private val existingEmail = "cabrerajjorge@gmail.com"
    private val existingPhone = "+5491169004107"
    private val oneExistingUser = User(
            id = 1L,
            status = ACTIVE,
            email = existingEmail,
            phone = existingPhone,
            firstName = "jorge",
            lastName = "cabrera",
            lastModifiedDate = LocalDateTime.now(),
            createdDate = LocalDateTime.now()
    )

    private val twoExistingUser = User(
            id = userIdToUpdate,
            status = ACTIVE,
            email = "otro@email.com",
            phone = "otro@ohone.com",
            firstName = "jorge",
            lastName = "cabrera",
            lastModifiedDate = LocalDateTime.now(),
            createdDate = LocalDateTime.now()
    )

    @BeforeEach
    private fun setUp() {
        MockitoAnnotations.initMocks(this)
        userFactory = UserBuilder(listOf(
                EmailAddressPatternRule(),
                NotEmptyFirstNameRule(),
                NotEmptyLastNameRule(),
                PhoneNumberPatternRule(),
                EmailUnusedRule(userRepository),
                PhoneUnusedRule(userRepository)))
        updateUserData = UpdateUserData(userRepository, userFactory)
        `when`(userRepository.findByEmail(existingEmail)).thenReturn(oneExistingUser)
        `when`(userRepository.findByPhone(existingPhone)).thenReturn(oneExistingUser)
        `when`(userRepository.findById(userIdToUpdate)).thenReturn(twoExistingUser)

    }

    @Test
    fun `when try to set an email already used by other user then should throw exception`() {
        //given
        val userData = UserDto(email = existingEmail, phone = "+5491127389201", firstName = "jorge", lastName = "cabrera")

        //then
        Assertions.assertThrows(EmailAlreadyInUseException::class.java) {
            updateUserData.execute(userData, userIdToUpdate)
        }
    }

    @Test
    fun `when try to set a phone already used by other user then should throw exception`() {
        //given
        val userData = UserDto(email = "jorgejcabrera@gmail.com", phone = existingPhone, firstName = "jorge", lastName = "cabrera")

        //then
        Assertions.assertThrows(PhoneAlreadyInUseException::class.java) {
            updateUserData.execute(userData, userIdToUpdate)
        }
    }

    @Test
    fun `when try to set an invalid email then should throw exception`() {
        //given
        val invalidUserData = UserDto(email = "jorgejcabrera@.com", phone = "+5491127389201", firstName = "jorge", lastName = "cabrera")

        //then
        Assertions.assertThrows(InvalidEmailAddressException::class.java) {
            updateUserData.execute(invalidUserData, userIdToUpdate)
        }
    }

    @Test
    fun `when user not exists then should throw exception`() {
        //then
        Assertions.assertThrows(UserNotFoundException::class.java) {
            updateUserData.execute(UserDto(), unExistingUserId)
        }
    }

    @Test
    fun `when email is not being used and has valid format then should update successfully`() {
        //given
        val validUserdata = UserDto(email = "jorge.cabrera@email.com", phone = "+5591169004107", firstName = "jorge", lastName = "cabrera")

        //when
         updateUserData.execute(validUserdata, userIdToUpdate)

        //then
        noExceptionWasThrown()
    }

    private fun noExceptionWasThrown() {
        //nothing to do
    }

}