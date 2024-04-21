package com.prants.controller

import com.prants.api.forms.NewReaderForm
import com.prants.entity.Reader
import com.prants.repository.ReaderRepository
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.time.LocalDateTime

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@MicronautTest
class ReaderControllerTest {

    private BlockingHttpClient blockingClient

    @Inject
    @Client("/")
    HttpClient client

    @Inject
    ReaderRepository readerRepository

    @BeforeEach
    void setup() {
        blockingClient = client.toBlocking()
    }

    @Test
    void saveNewReaderSuccess() {
        String expectedValue = "abc"
        when(readerRepository.getReaderWithReaderCode("abc")).thenReturn(Optional.empty())
        when(readerRepository.saveNewReader(buildTestReaderNoId())).thenReturn(buildTestReader())
        HttpRequest<?> request = HttpRequest.POST("/reader", buildNewReaderForm())
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void saveNewReaderFailOnBadRequest() {
        HttpRequest<?> request = HttpRequest.POST("/reader", buildBrokenReaderForm())
        assertThrows(HttpClientResponseException.class, () ->
                blockingClient.exchange(request)
        )
    }

    @Test
    void findAllReadersWithOneHit() {
        String expectedValue = "[{\"name\":\"Test Reader\",\"readerCode\":\"abc\"}]"
        when(readerRepository.getAllReaders()).thenReturn(List.of(buildTestReader()))

        HttpRequest<?> request = HttpRequest.GET("/reader")
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void findAllReadersWithNoHit() {
        String expectedValue = "[]"
        when(readerRepository.getAllReaders()).thenReturn(List.of())

        HttpRequest<?> request = HttpRequest.GET("/reader")
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @MockBean(ReaderRepository.class)
    ReaderRepository readerRepository() {
        return mock(ReaderRepository.class)
    }

    NewReaderForm buildNewReaderForm() {
        NewReaderForm newForm = new NewReaderForm()
        newForm.setName("Test Reader")
        newForm.setReaderCode("abc")
        return newForm
    }

    NewReaderForm buildBrokenReaderForm() {
        NewReaderForm newForm = new NewReaderForm()
        newForm.setName("Test Reader")
        return newForm
    }

    Reader buildTestReader() {
        Reader newReader = buildTestReaderNoId()
        newReader.setId(1L)
        return newReader
    }

    Reader buildTestReaderNoId() {
        Reader newReader = new Reader()
        newReader.setName("Test Reader")
        newReader.setReaderCode("abc")
        newReader.setAddTime(LocalDateTime.of(2024, 04, 21, 8, 0, 0))
        return newReader
    }
}
