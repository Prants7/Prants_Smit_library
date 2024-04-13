package com.prants.entity

import java.time.LocalDate
import java.time.LocalDateTime

class Book {
    private Long id
    private String name
    //todo in future can do some unified author when app needs to expand
    private String author
    private LocalDate releaseDate
    private LocalDateTime addTime
}
