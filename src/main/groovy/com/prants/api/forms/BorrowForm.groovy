package com.prants.api.forms

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class BorrowForm {
    private String readerCode
    private Integer bookScanCode

    String getReaderCode() {
        return readerCode
    }

    void setReaderCode(String readerCode) {
        this.readerCode = readerCode
    }

    Integer getBookScanCode() {
        return bookScanCode
    }

    void setBookScanCode(Integer bookScanCode) {
        this.bookScanCode = bookScanCode
    }


    @Override
    String toString() {
        return "BorrowForm{" +
                "readerCode='" + readerCode + '\'' +
                ", bookScanCode=" + bookScanCode +
                '}'
    }
}
