package com.prants.service

import com.prants.api.display.BookDisplayElement
import com.prants.api.display.BorrowDisplayElement
import com.prants.api.display.ReaderDisplayElement
import com.prants.entity.Book
import com.prants.entity.BorrowInstance
import com.prants.repository.TempBookCopyStorage
import com.prants.repository.TempBookStorage
import com.prants.repository.TempReaderStorage
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class DisplayPrepService {
    @Inject
    TempBookCopyStorage bookCopyStorage

    //todo can move it to db later
    BookDisplayElement prepareBookDisplayElement(Book fromBook) {
        BookDisplayElement newDisplayElement = new BookDisplayElement()
        newDisplayElement.setId(fromBook.getId())
        newDisplayElement.setName(fromBook.getName())
        newDisplayElement.setAuthor(fromBook.getAuthor())
        newDisplayElement.setReleaseDate(fromBook.getReleaseDate())
        newDisplayElement.setAllCopyCount(bookCopyStorage.getAmountOfTotalCopiesForBook(fromBook))
        newDisplayElement.setAvailableCopyCount(bookCopyStorage.getAmountOfAvailableCopiesForBook(fromBook))
        return newDisplayElement
    }

    ReaderDisplayElement prepareReaderDisplayElement(com.prants.entity.Reader fromReader) {
        ReaderDisplayElement newReaderDisplayElement = new ReaderDisplayElement()
        newReaderDisplayElement.setName(fromReader.name)
        newReaderDisplayElement.setReaderCode(fromReader.getReaderCode())
        return newReaderDisplayElement
    }

    BorrowDisplayElement prepareBorrowDisplayElement(BorrowInstance fromBorrowInstance) {
        BorrowDisplayElement newBorrowDisplayElement = new BorrowDisplayElement()
        newBorrowDisplayElement.setBookId(fromBorrowInstance.getBorrowedCopy().getBookType().getId())
        newBorrowDisplayElement.setBookName(fromBorrowInstance.getBorrowedCopy().getBookType().getName())
        newBorrowDisplayElement.setBookCopyScanCode(fromBorrowInstance.getBorrowedCopy().getScanCode())
        newBorrowDisplayElement.setReaderName(fromBorrowInstance.getBorrower().getName())
        newBorrowDisplayElement.setReaderCode(fromBorrowInstance.getBorrower().getReaderCode())
        newBorrowDisplayElement.setDayWhenBorrowed(fromBorrowInstance.getDayWhenBorrowed())
        newBorrowDisplayElement.setDesignatedReturnDate(fromBorrowInstance.getDesignatedReturnDate())
        return newBorrowDisplayElement
    }
}
