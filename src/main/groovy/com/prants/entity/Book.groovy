package com.prants.entity


import com.prants.api.forms.NewBookForm
import com.prants.service.TimeService
import groovy.transform.CompileStatic
import jakarta.annotation.Nonnull
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

import java.time.LocalDate
import java.time.LocalDateTime

@CompileStatic
@Entity
@Table(name = 'book')
@SequenceGenerator(name="book_seq", allocationSize=1)
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    private Long id
    @Nonnull
    private String name
    @Nonnull
    private String author
    @Column(name = 'release_date')
    private LocalDate releaseDate
    @Column(name = 'add_time')
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
