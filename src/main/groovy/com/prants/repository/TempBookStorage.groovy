package com.prants.repository

import com.prants.entity.Book
import io.micronaut.context.annotation.Bean

@Bean
class TempBookStorage {
    private Map<Long, Book> tempStorage = new HashMap<>()
    private Long idCounter = 0L

    Book saveNewBook(Book newBook) {
        newBook.setId(this.getIdFromCounter())
        tempStorage.put(newBook.getId(), newBook)
        System.out.println("saved new book " + newBook.toString())
        return newBook
    }

    private Long getIdFromCounter() {
        Long idToGive = idCounter
        idCounter++
        return idToGive
    }

    boolean isBookNameInUse(String bookName) {
        return tempStorage.values().stream().anyMatch(oneBook -> oneBook.name == bookName)
    }

    Optional<Book> findBookWithId(Long bookId) {
        if(tempStorage.containsKey(bookId)) {
            return Optional.of(tempStorage.get(bookId))
        }
        return Optional.empty()
    }
}
