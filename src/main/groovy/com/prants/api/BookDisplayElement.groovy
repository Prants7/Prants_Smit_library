package com.prants.api

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

import java.time.LocalDate

@Introspected
@Serdeable
class BookDisplayElement {
    private Long id
    private String name
    private String author
    private LocalDate releaseDate
    private Integer allCopyCount
    private Integer availableCopyCount

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getAuthor() {
        return author
    }

    void setAuthor(String author) {
        this.author = author
    }

    LocalDate getReleaseDate() {
        return releaseDate
    }

    void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate
    }

    Integer getAllCopyCount() {
        return allCopyCount
    }

    void setAllCopyCount(Integer allCopyCount) {
        this.allCopyCount = allCopyCount
    }

    Integer getAvailableCopyCount() {
        return availableCopyCount
    }

    void setAvailableCopyCount(Integer availableCopyCount) {
        this.availableCopyCount = availableCopyCount
    }
}
