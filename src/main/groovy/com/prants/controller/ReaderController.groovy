package com.prants.controller

import com.prants.api.NewBookForm
import com.prants.api.NewReaderForm
import com.prants.service.ReaderService
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
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
            return HttpResponse.badRequest()
        }
        return HttpResponse.created(returnId)
    }
}
