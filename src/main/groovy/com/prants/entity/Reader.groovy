package com.prants.entity


import com.prants.api.forms.NewReaderForm
import com.prants.service.TimeService
import groovy.transform.CompileStatic
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

import java.time.LocalDateTime

@CompileStatic
@Entity
@Table(name = 'reader')
@SequenceGenerator(name="reader_seq", allocationSize=1)
class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reader_seq")
    private Long id
    @Column(name = "name", nullable = false)
    private String name
    @Column(name = "reader_code", nullable = false, unique = true)
    private String readerCode
    @Column(name = "add_time", nullable = false)
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

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Reader reader = (Reader) o

        if (id != reader.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
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
