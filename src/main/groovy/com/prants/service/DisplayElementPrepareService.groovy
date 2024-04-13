package com.prants.service

import com.prants.api.BookDisplayElement
import com.prants.entity.Book
import com.prants.repository.TempBookCopyStorage
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class DisplayElementPrepareService {
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
}
