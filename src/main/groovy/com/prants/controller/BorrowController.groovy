package com.prants.controller

import com.prants.api.forms.BorrowForm
import com.prants.api.forms.ReturnForm
import com.prants.service.BorrowService
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

@CompileStatic
@Controller("/borrow")
class BorrowController {
    private BorrowService borrowService

    BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService
    }

    @Post()
    HttpResponse<?> borrowBook(@Body BorrowForm newBorrowForm) {
        Long returnId
        try {
            returnId = borrowService.saveNewBorrowInstance(newBorrowForm)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest()
        }
        return HttpResponse.created(returnId)
    }

    @Put("/return")
    HttpResponse<?> returnBook(@Body ReturnForm returnForm) {
        try {
            borrowService.returnBookCopy(returnForm)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest()
        }
        return HttpResponse.accepted()
    }
}
