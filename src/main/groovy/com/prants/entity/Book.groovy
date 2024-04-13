package com.prants.entity


import com.prants.api.forms.NewBookForm
import com.prants.service.TimeService

import java.time.LocalDate
import java.time.LocalDateTime

class Book {
    private Long id
    private String name
    private String author
    private LocalDate releaseDate
    private LocalDateTime addTime

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

    LocalDateTime getAddTime() {
        return addTime
    }

    void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime
    }

    static Book newBookFromForm(NewBookForm bookForm) {
        Book newBook = new Book()
        newBook.setName(bookForm.name)
        newBook.setAuthor(bookForm.author)
        newBook.setReleaseDate(bookForm.releaseDate)
        newBook.setAddTime(TimeService.currentDateTime)
        return newBook
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Book book = (Book) o

        if (id != book.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
    }

    @Override
    String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate=" + releaseDate +
                ", addTime=" + addTime +
                '}'
    }
}
