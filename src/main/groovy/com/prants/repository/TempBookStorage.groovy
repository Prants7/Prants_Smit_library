package com.prants.repository

import com.prants.entity.Book
import jakarta.inject.Singleton

@Singleton
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

    Optional<Book> findBookWithId(Long bookId) {
        if(tempStorage.containsKey(bookId)) {
            return Optional.of(tempStorage.get(bookId))
        }
        return Optional.empty()
    }

    List<Book> getAllBooks() {
        return new ArrayList<Book>(this.tempStorage.values())
    }
}
