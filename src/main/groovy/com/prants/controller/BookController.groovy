package com.prants.controller

import com.prants.api.display.BookCopyDisplayElement
import com.prants.api.display.BookDisplayElement
import com.prants.api.forms.NewBookCopyForm
import com.prants.api.forms.NewBookForm
import com.prants.service.BookService
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

@CompileStatic
@Controller("/book")
class BookController {
    BookService bookService

    BookController(BookService bookService) {
        this.bookService = bookService
    }

    @Get()
    HttpResponse<?> findAllBooks() {
        List<BookDisplayElement> bookDisplayElementList
        try {
            bookDisplayElementList = this.bookService.getAllBookBrowseList()
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.serverError(exception.getMessage())
        }
        return HttpResponse.ok(bookDisplayElementList)
    }

    //todo integration tests for this
    @Get("/{bookId}/available")
    HttpResponse<?> findBookScanCodes(@PathVariable Long bookId) {
        List<BookCopyDisplayElement> bookDisplayElementList
        try {
            bookDisplayElementList = this.bookService.getAllBookScanCodes(bookId)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.serverError(exception.getMessage())
        }
        return HttpResponse.ok(bookDisplayElementList)
    }

    @Post()
    HttpResponse<?> saveNewBook(@Body NewBookForm newBookForm) {
        Long returnId
        try {
            returnId = bookService.saveNewBook(newBookForm)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest(exception.getMessage())
        }
        return HttpResponse.created(returnId)
    }

    @Post("/copy")
    HttpResponse<?> saveNewBookCopy(@Body NewBookCopyForm newBookCopyForm) {
        Long returnId
        try {
            returnId = bookService.saveNewBookCopy(newBookCopyForm)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest(exception.getMessage())
        }
        return HttpResponse.created(returnId)
    }
}
