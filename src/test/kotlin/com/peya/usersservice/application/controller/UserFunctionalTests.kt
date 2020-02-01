package com.peya.usersservice.application.controller

import com.peya.usersservice.application.dto.UserDto
import com.peya.usersservice.domain.builder.UserBuilder
import com.peya.usersservice.domain.entity.User
import com.peya.usersservice.domain.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

const val userIdNotExisting = 1L

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserFunctionalTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var userRepository: UserRepository

    @Before
    fun clear() {
        userRepository.deleteAll()
    }

    @Test
    fun `when user does not exists then should throw not found exception`() {
        mockMvc.perform(get("/${UserController.path}/$userIdNotExisting")).andExpect(status().isNotFound)
    }

    @Test
    fun `when body is valid then can create a user`() {
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

    @Test
    fun `when body is invalid then should throw bad request exception`() {
        mockMvc.perform(post("/${UserController.path}")
                .content("""{ "first_name:"foo", "last_name":"bar", "email":"foo" }""".trimMargin())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `when delete an existing user then should work ok`() {
        val user = createAnyActiveUser()
        mockMvc.perform(delete("/${UserController.path}/${user.id}")).andExpect(status().is2xxSuccessful)
    }

    @Test
    fun `when change user name then should change it ok`() {
        // given
        val user = createAnyActiveUser()

        // when
        mockMvc.perform(put("/${UserController.path}/${user.id}")
                .content("""{ "first_name":"jorge" }""".trimMargin())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful)


        // then
        mockMvc.perform(get("/${UserController.path}/${user.id}"))
                .andExpect(jsonPath("first_name").value("jorge"))
                .andExpect(status().is2xxSuccessful)

    }

    private fun createAnyActiveUser(): User {
        val user = UserBuilder().withUserDto(UserDto(firstName = "Martin", email = "martin.bosch@gmail.com", lastName = "bosch")).build()
        return userRepository.save(user)
    }
}