package com.prants.service

import com.prants.api.NewBookCopyForm
import com.prants.api.NewBookForm
import com.prants.entity.Book
import com.prants.entity.BookCopy
import com.prants.repository.TempBookCopyStorage
import com.prants.repository.TempBookStorage
import io.micronaut.context.annotation.Bean

@Bean
class BookService {
    private TempBookStorage bookStorage
    private TempBookCopyStorage bookCopyStorage

    BookService(TempBookStorage bookStorage,
                TempBookCopyStorage bookCopyStorage) {
        this.bookStorage = bookStorage
        this.bookCopyStorage = bookCopyStorage
    }

    Long saveNewBook(NewBookForm newBookForm) {
        isFormValid(newBookForm)
        Book newBook = Book.newBookFromForm(newBookForm)
        Book savedBook = bookStorage.saveNewBook(newBook)
        return savedBook.getId()
    }

    Long saveNewBookCopy(NewBookCopyForm newBookCopyForm) {
        isFormValid(newBookCopyForm)
        BookCopy newBookCopy = BookCopy.newBookCopyFromForm(newBookCopyForm, this.bookStorage)
        BookCopy savedBookCopy = bookCopyStorage.saveNewBookCopy(newBookCopy)
        return savedBookCopy.getId()
    }

    private void isFormValid(NewBookForm newBookForm) {
        if (newBookForm.getName() == null) {
            throw new RuntimeException("Form is missing book name")
        }
        if (newBookForm.getAuthor() == null) {
            throw new RuntimeException("Form is missing author")
        }
        if (newBookForm.getReleaseDate() == null) {
            throw new RuntimeException("Form is missing release date")
        }
        if (bookStorage.isBookNameInUse(newBookForm.name)) {
            throw new RuntimeException("Book name is already in use")
        }
    }

    private void isFormValid(NewBookCopyForm newBookCopyForm) {
        if (newBookCopyForm.bookId == null) {
            throw new RuntimeException("Form is missing book id")
        }
        if (newBookCopyForm.scanCode == null) {
            throw new RuntimeException("Form is missing scan code")
        }
        if (bookStorage.findBookWithId(newBookCopyForm.getBookId()).isEmpty()) {
            throw new RuntimeException("No book with id in form")
        }
        if (bookCopyStorage.findBookCopyWithScanCode(newBookCopyForm.getScanCode()).isPresent()) {
            throw new RuntimeException("Scan code already in use")
        }
    }



}
