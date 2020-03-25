package com.peya.usersservice.application.controller

import com.peya.usersservice.domain.user.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class RetrieveUserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var userRepository: UserRepository

    @Before
    fun clear() {
        userRepository.deleteAll()
    }

    @Test
    fun `when user does not exists then should return not found`() {
        val userIdNotExisting = 1L
        mockMvc.perform(get("/user/$userIdNotExisting"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("code").value("USER_NOT_EXIST"))
                .andExpect(jsonPath("messages[0]").value("User does not exist"))
    }

}