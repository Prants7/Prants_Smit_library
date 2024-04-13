package com.prants.api

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class NewReaderForm {
    private String name
    //todo make this some randomly generate code later so we can protect people from getting asked about
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


    @Override
    String toString() {
        return "NewReaderForm{" +
                "name='" + name + '\'' +
                ", readerCode='" + readerCode + '\'' +
                '}'
    }
}
