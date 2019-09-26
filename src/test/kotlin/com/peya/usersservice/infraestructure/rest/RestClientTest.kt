package com.peya.usersservice.infraestructure.rest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RestClientTest {

    @Autowired
    private lateinit var restClient: RestClient

    @Test
    fun `test get method should work ok`() {
        val url = "https://api.mercadolibre.com/users/123654"
        var response = restClient.get(url)
        assertThat(response).isNotNull
    }
}