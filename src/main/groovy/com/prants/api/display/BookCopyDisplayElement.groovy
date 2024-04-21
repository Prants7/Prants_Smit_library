package com.prants.api.display

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class BookCopyDisplayElement {
    private Integer scanCode

    Integer getScanCode() {
        return scanCode
    }

    void setScanCode(Integer scanCode) {
        this.scanCode = scanCode
    }
}
