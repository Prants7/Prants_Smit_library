package com.prants.entity

import com.prants.api.forms.NewBookCopyForm
import com.prants.repository.BookRepository
import com.prants.service.TimeService
import groovy.transform.CompileStatic
import jakarta.annotation.Nonnull
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

import java.time.LocalDateTime

@CompileStatic
@Entity
@Table(name = 'book_copy')
@SequenceGenerator(name="book_copy_seq", allocationSize=1)
class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_copy_seq")
    private Long id
    @Nonnull
    @ManyToOne
    @JoinColumn(name = "book_type")
    private Book bookType
    @Column(name = "scan_code", nullable = false, unique = true)
    private Integer scanCode
    @Column(name = "add_time", nullable = false)
    private LocalDateTime addDate

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    Book getBookType() {
        return bookType
    }

    void setBookType(Book bookType) {
        this.bookType = bookType
    }

    Integer getScanCode() {
        return scanCode
    }

    void setScanCode(Integer scanCode) {
        this.scanCode = scanCode
    }

    LocalDateTime getAddDate() {
        return addDate
    }

    void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate
    }

    static BookCopy newBookCopyFromForm(NewBookCopyForm newBookCopyForm, BookRepository bookStorage) {
        BookCopy newBookCopy = new BookCopy()
        Optional<Book> matchingBook = bookStorage.findById(newBookCopyForm.bookId)
        if (matchingBook.isEmpty()) {
            throw new RuntimeException("Trying to build book copy with bad book id:" + newBookCopyForm.bookId)
        }
        newBookCopy.setBookType(matchingBook.get())
        newBookCopy.setScanCode(newBookCopyForm.scanCode)
        newBookCopy.setAddDate(TimeService.getCurrentDateTime())
        return newBookCopy
    }


    @Override
    String toString() {
        return "BookCopy{" +
                "id=" + id +
                ", bookType=" + bookType +
                ", scanCode=" + scanCode +
                ", addDate=" + addDate +
                '}'
    }
}
