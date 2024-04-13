package com.prants.controllers

import groovy.transform.CompileStatic
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@CompileStatic
@Controller("/hello")
class HelloController {

    @Get
    //default would be json so remove this for realistic ones
    @Produces(MediaType.TEXT_PLAIN)
    String index() {
        "Hello World"
    }
}
