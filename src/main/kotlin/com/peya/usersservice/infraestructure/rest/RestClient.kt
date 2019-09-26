package com.peya.usersservice.infraestructure.rest

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.Version.HTTP_2
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

@Component
class RestClient {

    @Value("\${restclient.timeout}")
    private val timeout: Int = 0
    private val client = HttpClient.newHttpClient()

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @Throws(IOException::class, InterruptedException::class)
    operator fun get(uri: String): JsonObject {
        val request = HttpRequest.newBuilder()
                .version(HTTP_2)
                .uri(URI.create(uri))
                .GET()
                .timeout(Duration.ofMillis(timeout!!.toLong()))
                .header("content-type", "application/json")
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != HttpStatus.OK.value()) {
            logger.error("It was an error while trying to get resource from $uri.")
        }
        return Gson().fromJson(response.body(), JsonObject::class.java)
    }
}