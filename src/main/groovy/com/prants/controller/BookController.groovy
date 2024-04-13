package com.prants.controller

import com.prants.api.NewBookForm
import com.prants.service.BookService
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@CompileStatic
@Controller("/book")
class BookController {
    BookService bookService

    BookController(BookService bookService) {
        this.bookService = bookService
    }

    @Post()
    HttpResponse<?> saveNewBook(@Body NewBookForm newBookForm) {
        Long returnId
        try {
            returnId = bookService.saveNewBook(newBookForm)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest()
        }
        return HttpResponse.created(returnId)
    }
}
