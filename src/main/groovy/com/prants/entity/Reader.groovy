package com.prants.entity


import com.prants.api.NewReaderForm
import com.prants.service.TimeService

import java.time.LocalDateTime

class Reader {
    private Long id
    private String name
    private String readerCode
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

    String getReaderCode() {
        return readerCode
    }

    void setReaderCode(String readerCode) {
        this.readerCode = readerCode
    }

    LocalDateTime getAddTime() {
        return addTime
    }

    void setAddTime(LocalDateTime addDate) {
        this.addTime = addDate
    }

    static Reader newReaderFromForm(NewReaderForm readerForm) {
        Reader newReader = new Reader()
        newReader.setName(readerForm.getName())
        newReader.setReaderCode(readerForm.getReaderCode())
        newReader.setAddTime(TimeService.currentDateTime)
        return newReader
    }


    @Override
    String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", readerCode='" + readerCode + '\'' +
                ", addTime=" + addTime +
                '}'
    }
}
