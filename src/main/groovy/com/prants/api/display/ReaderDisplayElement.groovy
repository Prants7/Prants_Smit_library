package com.prants.api.display

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class ReaderDisplayElement {
    private String name
    private String readerCode

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getReaderCode() {
        return readerCode
    }

    void setReaderCode(String readerCode) {
        this.readerCode = readerCode
    }
}
