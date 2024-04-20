package com.prants.api.display

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

import java.time.LocalDate

@Introspected
@Serdeable
class BorrowDisplayElement {
    private Long borrowId
    private Long bookId
    private String bookName
    private Integer bookCopyScanCode
    private String readerName
    private String readerCode
    private LocalDate dayWhenBorrowed
    private LocalDate designatedReturnDate

    Long getBorrowId() {
        return borrowId
    }

    void setBorrowId(Long borrowId) {
        this.borrowId = borrowId
    }

    Long getBookId() {
        return bookId
    }

    void setBookId(Long bookId) {
        this.bookId = bookId
    }

    String getBookName() {
        return bookName
    }

    void setBookName(String bookName) {
        this.bookName = bookName
    }

    Integer getBookCopyScanCode() {
        return bookCopyScanCode
    }

    void setBookCopyScanCode(Integer bookCopyScanCode) {
        this.bookCopyScanCode = bookCopyScanCode
    }

    String getReaderName() {
        return readerName
    }

    void setReaderName(String readerName) {
        this.readerName = readerName
    }

    String getReaderCode() {
        return readerCode
    }

    void setReaderCode(String readerCode) {
        this.readerCode = readerCode
    }

    LocalDate getDayWhenBorrowed() {
        return dayWhenBorrowed
    }

    void setDayWhenBorrowed(LocalDate dayWhenBorrowed) {
        this.dayWhenBorrowed = dayWhenBorrowed
    }

    LocalDate getDesignatedReturnDate() {
        return designatedReturnDate
    }

    void setDesignatedReturnDate(LocalDate designatedReturnDate) {
        this.designatedReturnDate = designatedReturnDate
    }
}
