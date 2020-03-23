package com.peya.usersservice.application.controller

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.builder.UserBuilder
import com.peya.usersservice.domain.entity.User
import com.peya.usersservice.domain.repository.AuthRepository
import com.peya.usersservice.domain.repository.UserRepository
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserFunctionalTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var userRepository: UserRepository
    @MockBean
    private lateinit var authRepository: AuthRepository

    @Before
    fun clear() {
        userRepository.deleteAll()
    }

    @Test
    fun `when user does not exists then should return not found`() {
        val userIdNotExisting = 1L
        mockMvc.perform(get("/${UserController.path}/$userIdNotExisting"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("code").value("USER_NOT_EXIST"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("User does not exist"))
    }

    @Test
    fun `when body is valid then can create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "last_name":"bar", "email":"foo@email.com", "phone":"123" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("first_name").value("foo"))
                .andExpect(jsonPath("last_name").value("bar"))
                .andExpect(jsonPath("email").value("foo@email.com"))
                .andExpect(jsonPath("status").value("ACTIVE"))
                .andExpect(jsonPath("phone").value("123"))
                .andExpect(jsonPath("created_date").exists())
                .andExpect(jsonPath("last_modified_date").exists())
    }

    @Test
    fun `when delete an existing user then should work ok`() {
        val user = givenAnyActiveUser()
        mockMvc.perform(delete("/${UserController.path}/${user.id}"))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun `when try to delete a not existing user then should return not found`() {
        val userIdNotExisting = 1L
        mockMvc.perform(delete("/${UserController.path}/${userIdNotExisting}"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("code").value("USER_NOT_EXIST"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("User does not exist"))
    }

    @Test
    fun `when change user name then should change it ok`() {
        val user = givenAnyActiveUser()
        val newFirstName = "jorge"

        mockMvc.perform(put("/${UserController.path}/${user.id}")
                .content("""{ 
                    |"first_name":"$newFirstName",
                    |"last_name":"${user.lastName}",
                    |"email":"${user.email}",
                    |"phone":"${user.phone}" }"""
                        .trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful)

        mockMvc.perform(get("/${UserController.path}/${user.id}"))
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("first_name").value(newFirstName))
                .andExpect(jsonPath("last_name").value(user.lastName))
                .andExpect(jsonPath("email").value(user.email))
                .andExpect(jsonPath("phone").value(user.phone))
    }

    @Test
    fun `when try to change a not existing user should return not found`() {
        val user = givenAnyActiveUser()
        val newFirstName = "jorge"
        val userIdNotExisting = 1L

        mockMvc.perform(put("/${UserController.path}/${userIdNotExisting}")
                .content("""{ 
                    |"first_name":"$newFirstName",
                    |"last_name":"${user.lastName}",
                    |"email":"${user.email}",
                    |"phone":"${user.phone}" }"""
                        .trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("code").value("USER_NOT_EXIST"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("User does not exist"))
    }

    @Test
    fun `first name is required for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "last_name":"bar", "email":"foo@email.com", "phone":"123" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("MISSING_REQUIRED_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("field 'first_name' is required"))
    }

    @Test
    fun `first name must not be empty for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"", "last_name":"bar", "email":"foo@email.com", "phone":"123" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("INVALID_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("'firstName' must not be empty"))
    }

    @Test
    fun `last name is required for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "email":"foo@email.com", "phone":"123" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("MISSING_REQUIRED_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("field 'last_name' is required"))
    }

    @Test
    fun `last name must not be empty for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "last_name":"", "email":"foo@email.com", "phone":"123" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("INVALID_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("'lastName' must not be empty"))
    }

    @Test
    fun `phone is required for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "last_name":"bar", "email":"foo@email.com" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("MISSING_REQUIRED_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("field 'phone' is required"))
    }

    @Test
    fun `phone must not be empty for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "last_name":"bar", "email":"foo@email.com", "phone":"" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("INVALID_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(2)))
                .andExpect(jsonPath("messages",
                        containsInAnyOrder("'phone' must not be empty", "'phone' must match \"\\d+\""))
                )
    }

    @Test
    fun `phone must be valid for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "last_name":"bar", "email":"foo@email.com", "phone": "aa" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("INVALID_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("'phone' must match \"\\d+\""))
    }

    @Test
    fun `email is required for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "last_name":"bar", "phone":"123" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("MISSING_REQUIRED_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("field 'email' is required"))
    }

    @Test
    fun `email must not be empty for create a user`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "last_name":"bar", "phone":"123", "email":"" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("INVALID_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("'email' must not be empty"))
    }

    @Test
    fun `when email is invalid then should throw bad request exception`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name":"foo", "last_name":"bar", "email":"foo", "phone":"123" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("code").value("INVALID_FIELD"))
                .andExpect(jsonPath("messages.length()", `is`(1)))
                .andExpect(jsonPath("messages[0]").value("'email' must be a well-formed email address"))
    }


    private fun givenAnyActiveUser(): User {
        val user = UserBuilder().withUserDto(UserDto(firstName = "Martin", email = "martin.bosch@gmail.com", lastName = "bosch", phone = "123",password = "asd")).build()
        return userRepository.save(user)
    }
}