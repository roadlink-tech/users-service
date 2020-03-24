package com.peya.usersservice.infrastructure

import com.peya.usersservice.domain.auth.AuthRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.client.RestTemplate
import java.net.URI

class AuthRestClient : AuthRepository {

    override fun saveAuthRegistrationInfo(pwd: String, email: String, phone: String) {
        var client = RestTemplate()
        val headers = HttpHeaders()
        val url = "localhost:8081/auth/register"
        val uri = URI(url)
        val body = AuthBody(userId = 1, password = pwd)
        headers.contentType = APPLICATION_JSON
        client.postForEntity(uri, body, String::class.java)
    }
}

data class AuthBody(val userId: Long, val password: String)