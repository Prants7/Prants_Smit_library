package com.prants.controller

import com.prants.api.display.BorrowDisplayElement
import com.prants.api.forms.BorrowForm
import com.prants.api.forms.ReturnForm
import com.prants.service.BorrowService
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

@CompileStatic
@Controller("/borrow")
class BorrowController {
    private BorrowService borrowService

    BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService
    }

    @Get()
    HttpResponse<?> findAllActiveBorrows() {
        List<BorrowDisplayElement> borrowDisplayList
        try {
            borrowDisplayList = this.borrowService.getAllActiveBorrows()
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.serverError(exception.getMessage())
        }
        return HttpResponse.ok(borrowDisplayList)
    }

    //todo add integration tests for this
    @Get("/reader-code/{readerCode}")
    HttpResponse<?> findAllActiveBorrowsForReader(@PathVariable String readerCode) {
        List<BorrowDisplayElement> borrowDisplayList
        try {
            borrowDisplayList = this.borrowService.getAllActiveBorrowsForReader(readerCode)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.serverError(exception.getMessage())
        }
        return HttpResponse.ok(borrowDisplayList)
    }

    //todo add integration tests for this
    @Get("/book-id/{bookId}")
    HttpResponse<?> findAllActiveBorrowsForBook(@PathVariable Long bookId) {
        List<BorrowDisplayElement> borrowDisplayList
        try {
            borrowDisplayList = this.borrowService.getAllActiveBorrowsForBook(bookId)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest(exception.getMessage())
        }
        return HttpResponse.ok(borrowDisplayList)
    }

    @Post()
    HttpResponse<?> borrowBook(@Body BorrowForm newBorrowForm) {
        Long returnId
        try {
            returnId = borrowService.saveNewBorrowInstance(newBorrowForm)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest(exception.getMessage())
        }
        return HttpResponse.created(returnId)
    }

    @Put("/return")
    HttpResponse<?> returnBook(@Body ReturnForm returnForm) {
        try {
            borrowService.returnBookCopy(returnForm)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest(exception.getMessage())
        }
        return HttpResponse.accepted()
    }
}
