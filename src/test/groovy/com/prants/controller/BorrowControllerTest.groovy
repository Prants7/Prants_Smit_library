package com.prants.controller

import com.prants.api.forms.BorrowForm
import com.prants.api.forms.ReturnForm
import com.prants.entity.Book
import com.prants.entity.BookCopy
import com.prants.entity.BorrowInstance
import com.prants.entity.Reader
import com.prants.repository.BookCopyRepository
import com.prants.repository.BorrowRepository
import com.prants.repository.ReaderRepository
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
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
class BorrowControllerTest {

    private BlockingHttpClient blockingClient

    @Inject
    @Client("/")
    HttpClient client

    @Inject
    BorrowRepository borrowRepository
    @Inject
    ReaderRepository readerRepository
    @Inject
    BookCopyRepository bookCopyRepository

    @BeforeEach
    void setup() {
        blockingClient = client.toBlocking()
    }

    @Test
    void findAllActiveBorrowsWithOneHit() {
        String expectedValue = "[{\"borrowId\":1,\"bookId\":1,\"bookName\":\"Test Book\"," +
                "\"bookCopyScanCode\":123,\"readerName\":\"Test Reader\",\"readerCode\":\"abc\"," +
                "\"dayWhenBorrowed\":\"2010-01-01\",\"designatedReturnDate\":\"2010-01-08\"}]"
        when(borrowRepository.findAllActiveBorrows()).thenReturn(List.of(buildTestBorrow()))

        HttpRequest<?> request = HttpRequest.GET("/borrow")
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void findAllActiveBorrowsWithNoHit() {
        String expectedValue = "[]"
        when(borrowRepository.findAllActiveBorrows()).thenReturn(List.of())

        HttpRequest<?> request = HttpRequest.GET("/borrow")
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void borrowBookSuccess() {
        String expectedValue = "1"
        when(readerRepository.getReaderWithReaderCode("abc")).thenReturn(Optional.of(buildTestReader()))
        when(bookCopyRepository.findBookCopyWithScanCode(123)).thenReturn(Optional.of(buildTestBookCopy()))
        when(borrowRepository.getActiveBorrowInstanceForBookCode(123)).thenReturn(Optional.empty())
        when(borrowRepository.saveBorrowInstance(buildTestBorrowNoId())).thenReturn(buildTestBorrow())
        HttpRequest<?> request = HttpRequest.POST("/borrow", buildNewBorrowForm())
        String retrievedValue = blockingClient.retrieve(request)
        assertEquals(expectedValue, retrievedValue)
    }

    @Test
    void borrowBookFailOnBadRequest() {
        HttpRequest<?> request = HttpRequest.POST("/borrow", buildBrokenBorrowForm())
        assertThrows(HttpClientResponseException.class, () ->
                blockingClient.exchange(request)
        )
    }

    @Test
    void returnBookSuccess() {
        when(bookCopyRepository.findBookCopyWithScanCode(123)).thenReturn(Optional.of(buildTestBookCopy()))
        when(borrowRepository.getActiveBorrowInstanceForBookCode(123)).thenReturn(Optional.of(buildTestBorrow()))
        when(borrowRepository.returnBookWithScanCode(123)).thenReturn(1)
        HttpRequest<?> request = HttpRequest.PUT("/borrow/return", buildNewReturnForm())
        HttpResponse<?> response = blockingClient.exchange(request)
        assertEquals(HttpStatus.ACCEPTED, response.getStatus())
    }

    @Test
    void returnBookFailOnBadRequest() {
        HttpRequest<?> request = HttpRequest.PUT("/borrow/return", buildBrokenReturnForm())
        assertThrows(HttpClientResponseException.class, () ->
                blockingClient.exchange(request)
        )
    }

    @MockBean(BorrowRepository.class)
    BorrowRepository borrowRepository() {
        return mock(BorrowRepository.class)
    }

    @MockBean(ReaderRepository.class)
    ReaderRepository readerRepository() {
        return mock(ReaderRepository.class)
    }

    @MockBean(BookCopyRepository.class)
    BookCopyRepository bookCopyRepository() {
        return mock(BookCopyRepository.class)
    }

    BorrowInstance buildTestBorrow() {
        BorrowInstance newBorrow = buildTestBorrowNoId()
        newBorrow.setId(1L)
        return newBorrow
    }

    BorrowInstance buildTestBorrowNoId() {
        BorrowInstance newBorrow = new BorrowInstance()
        newBorrow.setBorrowedCopy(buildTestBookCopy())
        newBorrow.setBorrower(buildTestReader())
        newBorrow.setDayWhenBorrowed(LocalDate.of(2010, 1, 1))
        newBorrow.setDesignatedReturnDate(LocalDate.of(2010, 1, 8))
        return newBorrow
    }

    BookCopy buildTestBookCopy() {
        BookCopy newBookCopy = new BookCopy()
        newBookCopy.setScanCode(123)
        newBookCopy.setAddDate(LocalDateTime.of(2024, 04, 21, 8, 0, 0))
        newBookCopy.setBookType(buildTestBook())
        newBookCopy.setId(1L)
        return newBookCopy
    }

    Book buildTestBook() {
        Book newBook = new Book()
        newBook.setName("Test Book")
        newBook.setAuthor("Test Author")
        newBook.setReleaseDate(LocalDate.of(2010, 1, 1))
        newBook.setAddTime(LocalDateTime.of(2024, 04, 21, 8, 0, 0))
        newBook.setId(1L)
        return newBook
    }

    Reader buildTestReader() {
        Reader newReader = new Reader()
        newReader.setName("Test Reader")
        newReader.setReaderCode("abc")
        newReader.setAddTime(LocalDateTime.of(2024, 04, 21, 8, 0, 0))
        newReader.setId(1L)
        return newReader
    }

    BorrowForm buildNewBorrowForm() {
        BorrowForm newForm = new BorrowForm()
        newForm.setReaderCode("abc")
        newForm.setBookScanCode(123)
        return newForm
    }

    BorrowForm buildBrokenBorrowForm() {
        BorrowForm newForm = new BorrowForm()
        newForm.setReaderCode("abc")
        return newForm
    }

    ReturnForm buildNewReturnForm() {
        ReturnForm newForm = new ReturnForm()
        newForm.setBookScanCode(123)
        return newForm
    }

    ReturnForm buildBrokenReturnForm() {
        ReturnForm newForm = new ReturnForm()
        return newForm
    }
}
