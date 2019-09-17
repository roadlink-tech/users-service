package com.peya.usersservice.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `when user does not exists then API should throw not found exception`() {
        val entity = restTemplate.getForEntity<String>("/users/1")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}