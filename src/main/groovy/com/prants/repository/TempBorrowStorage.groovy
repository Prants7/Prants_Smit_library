package com.prants.repository

import com.prants.entity.Book
import com.prants.entity.BorrowInstance
import jakarta.inject.Singleton

@Singleton
class TempBorrowStorage {
    private Map<Long, BorrowInstance> tempStorage = new HashMap<>()
    private Long idCounter = 0L

    BorrowInstance saveBorrowInstance(BorrowInstance newBorrowInstance) {
        newBorrowInstance.setId(this.getIdFromCounter())
        tempStorage.put(newBorrowInstance.getId(), newBorrowInstance)
        System.out.println("saved new borrow instance: " + newBorrowInstance.toString())
        return newBorrowInstance
    }

    private Long getIdFromCounter() {
        Long idToGive = idCounter
        idCounter++
        return idToGive
    }

    boolean isBookWithScanCodeAlreadyBorrowedOut(Integer bookCopyScanCode) {
        return tempStorage.values().stream()
                .anyMatch(oneBorrowInstance ->
                        isThisBorrowedOutBookWithCode(oneBorrowInstance, bookCopyScanCode))
    }

    boolean isThisBorrowedOutBookWithCode(BorrowInstance oneBorrowInstance, Integer bookCopyScanCode) {
        if (oneBorrowInstance.getActualReturnDate() != null) {
            return false
        }
        if (oneBorrowInstance.getBorrowedCopy().scanCode != bookCopyScanCode) {
            return false
        }
        return true
    }

    Integer getAmountOfBorrowedOutCopiesForBook(Book targetBook) {
        return tempStorage.values().stream()
                .filter(oneBorrow -> isThisBorrowedOutCopyOfBook(oneBorrow, targetBook))
                .count()
    }

    boolean isThisBorrowedOutCopyOfBook(BorrowInstance oneBorrowInstance, Book targetBook) {
        if (oneBorrowInstance.getActualReturnDate() != null) {
            return false
        }
        if (oneBorrowInstance.getBorrowedCopy().getBookType() != targetBook) {
            return false
        }
        return true
    }
}
