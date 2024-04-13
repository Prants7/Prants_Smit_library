package com.prants.api.forms

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

import java.time.LocalDate

@Introspected
@Serdeable
class NewBookForm {
    private String name
    private String author
    private LocalDate releaseDate

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

    @Override
    String toString() {
        return "NewBookForm{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate=" + releaseDate +
                '}'
    }
}
