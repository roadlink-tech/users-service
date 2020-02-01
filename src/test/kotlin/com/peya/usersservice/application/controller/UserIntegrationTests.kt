package com.peya.usersservice.application.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTests() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Before
    fun clear() {
        userRepository.deleteAll()
    }


    @Test
    fun `when user does not exists then API should throw not found exception`() {
        mockMvc.perform(get("/${UserController.path}/1")).andExpect(status().isNotFound)
    }

    @Test
    fun `can save a user`() {
        mockMvc.perform(post("/${UserController.path}")
            .content("""{ "first_name":"foo", "last_name":"bar", "email":"foo@email.com" }""".trimMargin())
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("first_name").value("foo"))
            .andExpect(jsonPath("last_name").value("bar"))
            .andExpect(jsonPath("email").value("foo@email.com"))
            .andExpect(jsonPath("status").value("ACTIVE"))
            .andExpect(jsonPath("created_date").exists())
            .andExpect(jsonPath("last_modified_date").exists())
    }

}