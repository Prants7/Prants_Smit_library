package com.prants.repository

import com.prants.entity.Book
import com.prants.entity.Reader
import jakarta.inject.Singleton

@Singleton
class TempReaderStorage {

    private Map<Long, Reader> tempStorage = new HashMap<>()
    private Long idCounter = 0L

    Reader saveNewReader(Reader newReader) {
        newReader.setId(this.getIdFromCounter())
        tempStorage.put(newReader.getId(), newReader)
        System.out.println("saved new reader " + newReader.toString())
        return newReader
    }

    private Long getIdFromCounter() {
        Long idToGive = idCounter
        idCounter++
        return idToGive
    }

    boolean isReaderCodeInUse(String readerCode) {
        return tempStorage.values().stream()
                .anyMatch(oneReader -> oneReader.getReaderCode() == readerCode)
    }
}
