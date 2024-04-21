package com.prants.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

@MicronautTest
class HealthTest {

    private BlockingHttpClient blockingClient

    @Inject
    @Client("/")
    HttpClient client

    @BeforeEach
    void setup() {
        blockingClient = client.toBlocking()
    }

    @Test
    void healthEndpointExposed () {
        String expectedValue = "{\"status\":\"UP\"}"
        HttpRequest<?> request = HttpRequest.GET("/health")
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }
}
