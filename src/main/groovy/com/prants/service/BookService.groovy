package com.prants.service

import com.prants.api.NewBookForm
import com.prants.entity.Book
import com.prants.repository.TempBookStorage
import io.micronaut.context.annotation.Bean

@Bean
class BookService {
    private TempBookStorage bookStorage

    BookService(TempBookStorage bookStorage) {
        this.bookStorage = bookStorage
    }

    Long saveNewBook(NewBookForm newBookForm) {
        isFormValid(newBookForm)
        Book newBook = Book.newBookFromForm(newBookForm, TimeService.currentDateTime)
        Book savedBook = bookStorage.saveNewBook(newBook)
        return savedBook.id
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

}
