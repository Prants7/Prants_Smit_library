package com.prants.entity

import com.prants.api.forms.NewBookCopyForm
import com.prants.repository.BookRepository
import com.prants.service.TimeService

import java.time.LocalDateTime

class BookCopy {
    private Long id
    private Book bookType
    private Integer scanCode
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
