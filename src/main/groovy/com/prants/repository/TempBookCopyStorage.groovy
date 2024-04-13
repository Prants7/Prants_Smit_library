package com.prants.repository

import com.prants.entity.Book
import com.prants.entity.BookCopy
import jakarta.inject.Singleton

@Singleton
class TempBookCopyStorage {
    private Map<Long, BookCopy> tempStorage = new HashMap<>()
    private Long idCounter = 0L

    BookCopy saveNewBookCopy(BookCopy newBookCopy) {
        newBookCopy.setId(this.getIdFromCounter())
        tempStorage.put(newBookCopy.getId(), newBookCopy)
        System.out.println("saved new book copy " + newBookCopy.toString())
        return newBookCopy
    }

    private Long getIdFromCounter() {
        Long idToGive = idCounter
        idCounter++
        return idToGive
    }

    boolean isScanCodeInUse(Integer scanCode) {
        return tempStorage.values().stream()
                .anyMatch(oneBookCopy -> oneBookCopy.getScanCode() == scanCode)
    }

    Optional<BookCopy> findBookCopyWithId(Long bookCopyId) {
        if(tempStorage.containsKey(bookCopyId)) {
            return Optional.of(tempStorage.get(bookCopyId))
        }
        return Optional.empty()
    }

    Optional<BookCopy> findBookCopyWithScanCode(Integer scanCode) {
        return tempStorage.values().stream()
                .filter(oneBookCopy -> oneBookCopy.getScanCode() == scanCode).findFirst()
    }

    Integer getAmountOfTotalCopiesForBook(Book targetBook) {
        return tempStorage.values().stream()
                .filter(oneBookCopy -> oneBookCopy.getBookType() == targetBook).count()
    }

    //todo change when we got borrow instances implemented
    Integer getAmountOfAvailableCopiesForBook(Book targetBook) {
        getAmountOfTotalCopiesForBook(targetBook)
    }
}
