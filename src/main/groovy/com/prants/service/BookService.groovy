package com.prants.service

import com.prants.api.display.BookCopyDisplayElement
import com.prants.api.display.BookDisplayElement
import com.prants.api.forms.NewBookCopyForm
import com.prants.api.forms.NewBookForm
import com.prants.entity.Book
import com.prants.entity.BookCopy
import com.prants.repository.BookCopyRepository
import com.prants.repository.BookRepository

import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class BookService {
    @Inject
    private DisplayPrepService displayElementPrepareService
    @Inject
    private BookRepository bookRepository
    @Inject
    private BookCopyRepository bookCopyRepository

    Long saveNewBook(NewBookForm newBookForm) {
        isFormValid(newBookForm)
        Book newBook = Book.newBookFromForm(newBookForm)
        Book savedInRepository = bookRepository.saveNewBook(newBook)
        return savedInRepository.getId()
    }

    Long saveNewBookCopy(NewBookCopyForm newBookCopyForm) {
        isFormValid(newBookCopyForm)
        BookCopy newBookCopy = BookCopy.newBookCopyFromForm(newBookCopyForm, this.bookRepository)
        BookCopy savedBookCopy = bookCopyRepository.saveNewBookCopy(newBookCopy)
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
    }

    private void isFormValid(NewBookCopyForm newBookCopyForm) {
        if (newBookCopyForm.bookId == null) {
            throw new RuntimeException("Form is missing book id")
        }
        if (newBookCopyForm.scanCode == null) {
            throw new RuntimeException("Form is missing scan code")
        }
        if (bookRepository.findById(newBookCopyForm.getBookId()).isEmpty()) {
            throw new RuntimeException("No book with id in form")
        }
        if (bookCopyRepository.findBookCopyWithScanCode(newBookCopyForm.getScanCode()).isPresent()) {
            throw new RuntimeException("Scan code already in use")
        }
    }

    List<BookDisplayElement> getAllBookBrowseList() {
        List<Book> allBooks = this.bookRepository.findAllBooks()
        List<BookDisplayElement> returnList = allBooks.stream()
                .map(oneBook -> this.displayElementPrepareService.prepareBookDisplayElement(oneBook))
                .toList()
        return returnList
    }

    List<BookCopyDisplayElement> getAllBookScanCodes(Long bookId) {
        Book targetBook = this.bookRepository.findById(bookId).get()
        List<BookCopy> allAvailableCopies = this.bookCopyRepository.getAllCopiesForBook(targetBook)
        List<BookCopyDisplayElement> returnList = allAvailableCopies.stream()
                .map(oneBookCopy -> this.displayElementPrepareService.prepareBookCopyDisplayElement(oneBookCopy))
                .toList()
        return returnList
    }

}
