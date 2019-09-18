package com.peya.usersservice.service

import com.peya.usersservice.dto.UserDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun `when create user with invalid email should fail`() {
        val userDto = UserDto(firstName = "jorge",lastName = "cabrera")
        userService.createUser(userDto)
    }
}