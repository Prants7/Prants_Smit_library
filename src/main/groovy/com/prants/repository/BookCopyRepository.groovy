package com.prants.repository

import com.prants.entity.Book
import com.prants.entity.BookCopy

interface BookCopyRepository {

    BookCopy saveNewBookCopy(BookCopy newBookCopy)

    Optional<BookCopy> findBookCopyWithId(Long bookCopyId)

    Optional<BookCopy> findBookCopyWithScanCode(Integer scanCode)

    Integer getAmountOfTotalCopiesForBook(Book targetBook)

    Integer getAmountOfAvailableCopiesForBook(Book targetBook)

}