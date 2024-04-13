package com.prants.api

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class NewBookCopyForm {
    Long bookId
    Integer scanCode

    Long getBookId() {
        return bookId
    }

    void setBookId(Long bookId) {
        this.bookId = bookId
    }

    Integer getScanCode() {
        return scanCode
    }

    void setScanCode(Integer scanCode) {
        this.scanCode = scanCode
    }


    @Override
    String toString() {
        return "NewBookCopyForm{" +
                "bookId=" + bookId +
                ", scanCode=" + scanCode +
                '}'
    }
}
