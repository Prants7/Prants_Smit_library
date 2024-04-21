package com.prants.controller

import com.prants.api.display.BookDisplayElement
import com.prants.api.display.ReaderDisplayElement
import com.prants.api.forms.NewReaderForm
import com.prants.service.ReaderService
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

@CompileStatic
@Controller("/reader")
class ReaderController {
    ReaderService readerService

    ReaderController(ReaderService readerService) {
        this.readerService = readerService
    }

    @Post()
    HttpResponse<?> saveNewReader(@Body NewReaderForm newReaderForm) {
        String returnId
        try {
            returnId = readerService.saveNewReader(newReaderForm)
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.badRequest(exception.getMessage())
        }
        return HttpResponse.created(returnId)
    }

    @Get()
    HttpResponse<?> findAllReaders() {
        List<ReaderDisplayElement> readerDisplayList
        try {
            readerDisplayList = this.readerService.getAllReadersBrowseList()
        } catch (Exception exception) {
            System.out.println("got an exception " + exception)
            return HttpResponse.serverError(exception.getMessage())
        }
        return HttpResponse.ok(readerDisplayList)
    }
}
