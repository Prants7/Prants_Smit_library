package com.prants.api.forms

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class ReturnForm {
    private Integer bookScanCode

    Integer getBookScanCode() {
        return bookScanCode
    }

    void setBookScanCode(Integer bookScanCode) {
        this.bookScanCode = bookScanCode
    }

    @Override
    String toString() {
        return "ReturnForm{" +
                "bookScanCode=" + bookScanCode +
                '}'
    }
}
