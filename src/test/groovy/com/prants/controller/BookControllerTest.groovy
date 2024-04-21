package com.prants.controller

import com.prants.api.forms.NewBookCopyForm
import com.prants.api.forms.NewBookForm
import com.prants.entity.Book
import com.prants.entity.BookCopy
import com.prants.repository.BookCopyRepository
import com.prants.repository.BookRepository
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

import java.time.LocalDate
import java.time.LocalDateTime

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@MicronautTest
class BookControllerTest {

    private BlockingHttpClient blockingClient

    @Inject
    @Client("/")
    HttpClient client

    @Inject
    BookRepository bookRepository
    @Inject
    BookCopyRepository bookCopyRepository

    @BeforeEach
    void setup() {
        blockingClient = client.toBlocking()
    }

    @Test
    void findAllBooksWithOneHit() {
        String expectedValue = "[{\"id\":1,\"name\":\"Test Book\",\"author\":\"Test Author\"," +
                "\"releaseDate\":\"2010-01-01\",\"allCopyCount\":3,\"availableCopyCount\":3}]"
        when(bookRepository.findAllBooks()).thenReturn(List.of(buildTestBook()))

        HttpRequest<?> request = HttpRequest.GET("/book")
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void findAllBooksWithNoHit() {
        String expectedValue = "[]"
        when(bookRepository.findAllBooks()).thenReturn(List.of())

        HttpRequest<?> request = HttpRequest.GET("/book")
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void saveNewBookSuccess() {
        String expectedValue = "1"
        when(bookRepository.saveNewBook(buildTestBookNoId())).thenReturn(buildTestBook())
        HttpRequest<?> request = HttpRequest.POST("/book", buildNewBookForm())
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void saveNewBookFailOnBadRequest() {
        HttpRequest<?> request = HttpRequest.POST("/book", buildBrokenForm())
        assertThrows(HttpClientResponseException.class, () ->
                blockingClient.exchange(request)
        )
    }

    @Test
    void saveNewBookCopySuccess() {
        String expectedValue = "1"
        when(bookRepository.findById(1)).thenReturn(Optional.of(buildTestBook()))
        when(bookCopyRepository.findBookCopyWithScanCode(1)).thenReturn(Optional.empty())
        when(bookCopyRepository.saveNewBookCopy(buildTestBookCopyNoId())).thenReturn(buildTestBookCopy())
        HttpRequest<?> request = HttpRequest.POST("/book/copy", buildNewBookCopyForm())
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void saveNewBookCopyFailOnBadRequest() {
        HttpRequest<?> request = HttpRequest.POST("/book/copy", buildBrokenBookCopyForm())
        assertThrows(HttpClientResponseException.class, () ->
                blockingClient.exchange(request)
        )
    }

    @MockBean(BookRepository.class)
    BookRepository bookRepository() {
        return mock(BookRepository.class)
    }

    @MockBean(BookCopyRepository.class)
    BookCopyRepository bookCopyRepository() {
        return mock(BookCopyRepository.class)
    }

    Book buildTestBook() {
        Book newBook = buildTestBookNoId()
        newBook.setId(1L)
        return newBook
    }

    Book buildTestBookNoId() {
        Book newBook = new Book()
        newBook.setName("Test Book")
        newBook.setAuthor("Test Author")
        newBook.setReleaseDate(LocalDate.of(2010, 1, 1))
        newBook.setAddTime(LocalDateTime.of(2024, 04, 21, 8, 0, 0))
        return newBook
    }

    NewBookForm buildNewBookForm() {
        NewBookForm newForm = new NewBookForm()
        newForm.setName("Test Book")
        newForm.setAuthor("Test Author")
        newForm.setReleaseDate(LocalDate.of(2010, 1, 1))
        return newForm
    }

    NewBookForm buildBrokenForm() {
        NewBookForm newForm = new NewBookForm()
        newForm.setAuthor("Test Author")
        newForm.setReleaseDate(LocalDate.of(2010, 1, 1))
        return newForm
    }

    NewBookCopyForm buildNewBookCopyForm() {
        NewBookCopyForm newBookCopyForm = new NewBookCopyForm()
        newBookCopyForm.setBookId(1)
        newBookCopyForm.setScanCode(123)
        return newBookCopyForm
    }

    BookCopy buildTestBookCopy() {
        BookCopy newBookCopy = buildTestBookCopyNoId()
        newBookCopy.setId(1L)
        return newBookCopy
    }

    BookCopy buildTestBookCopyNoId() {
        BookCopy newBookCopy = new BookCopy()
        newBookCopy.setScanCode(123)
        newBookCopy.setAddDate(LocalDateTime.of(2024, 04, 21, 8, 0, 0))
        newBookCopy.setBookType(buildTestBook())
        return newBookCopy
    }

    NewBookCopyForm buildBrokenBookCopyForm() {
        NewBookCopyForm newBookCopyForm = new NewBookCopyForm()
        newBookCopyForm.setScanCode(123)
        return newBookCopyForm
    }
}
