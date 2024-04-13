package com.prants.service

import com.prants.api.display.BookDisplayElement
import com.prants.api.display.ReaderDisplayElement
import com.prants.entity.Book
import com.prants.repository.TempBookCopyStorage
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class DisplayPrepService {
    @Inject
    TempBookCopyStorage tempBookCopyStorage

    //todo can move it to db later
    BookDisplayElement prepareBookDisplayElement(Book fromBook) {
        BookDisplayElement newDisplayElement = new BookDisplayElement()
        newDisplayElement.setId(fromBook.getId())
        newDisplayElement.setName(fromBook.getName())
        newDisplayElement.setAuthor(fromBook.getAuthor())
        newDisplayElement.setReleaseDate(fromBook.getReleaseDate())
        newDisplayElement.setAllCopyCount(tempBookCopyStorage.getAmountOfTotalCopiesForBook(fromBook))
        newDisplayElement.setAvailableCopyCount(tempBookCopyStorage.getAmountOfAvailableCopiesForBook(fromBook))
        return newDisplayElement
    }

    ReaderDisplayElement prepareReaderDisplayElement(com.prants.entity.Reader fromReader) {
        ReaderDisplayElement newReaderDisplayElement = new ReaderDisplayElement()
        newReaderDisplayElement.setName(fromReader.name)
        newReaderDisplayElement.setReaderCode(fromReader.getReaderCode())
        return newReaderDisplayElement
    }
}
