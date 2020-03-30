package com.peya.usersservice.application.controller

import com.peya.usersservice.domain.user.User
import com.peya.usersservice.domain.user.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class RegisterUserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var userRepository: UserRepository

    private val usedEmail = "cabrerajjorge@gmail.com"

    @Before
    fun clear() {
        userRepository.deleteAll()
        userRepository.save(User(email = usedEmail))
    }

    @Test
    fun `when create user with an email already used then should throw 409`() {
        mockMvc.perform(post("/users")
                .content("""{ "first_name":"foo", "last_name":"cabrera", "email":"$usedEmail", "phone":"+5491169004107" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed)
    }

    @Test
    fun `last name must not be empty when create a user`() {
        mockMvc.perform(post("/users")
                .content("""{ "first_name":"foo", "last_name":"", "email":"foo@email.com", "phone":"+5491169004107" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when try to create a user with an invalid email`() {
        mockMvc.perform(post("/users")
                .content("""{ "first_name":"foo", "last_name":"cabrera", "email":"foo@", "phone":"+5491169004107" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when try to create a user without an email`() {
        mockMvc.perform(post("/users")
                .content("""{ "first_name":"foo", "last_name":"cabrera", "phone":"+5491169004107" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when try to create a user with an invalid phone`() {
        mockMvc.perform(post("/users")
                .content("""{ "first_name":"foo", "last_name":"cabrera", "email":"cabrerajjorge@gmail.com", "phone":"+asd" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when body is valid then can create a user`() {
        mockMvc.perform(post("/users")
                .content("""{ "first_name":"foo", "last_name":"bar", "email":"foo@email.com", "phone":"+5491169004107" }""".trimMargin())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("first_name").value("foo"))
                .andExpect(jsonPath("last_name").value("bar"))
                .andExpect(jsonPath("email").value("foo@email.com"))
                .andExpect(jsonPath("status").value("ACTIVE"))
                .andExpect(jsonPath("phone").value("+5491169004107"))
                .andExpect(jsonPath("created_date").exists())
                .andExpect(jsonPath("last_modified_date").exists())
    }
}