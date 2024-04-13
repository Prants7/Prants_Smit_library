package com.prants.service

import com.prants.api.display.BookDisplayElement
import com.prants.api.forms.NewBookCopyForm
import com.prants.api.forms.NewBookForm
import com.prants.entity.Book
import com.prants.entity.BookCopy
import com.prants.repository.TempBookCopyStorage
import com.prants.repository.TempBookStorage
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class BookService {
    @Inject
    private TempBookStorage bookStorage
    @Inject
    private TempBookCopyStorage bookCopyStorage
    @Inject
    private DisplayPrepService displayElementPrepareService

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
        if (bookCopyStorage.isScanCodeInUse(newBookCopyForm.getScanCode())) {
            throw new RuntimeException("Scan code already in use")
        }
    }

    List<BookDisplayElement> getAllBookBrowseList() {
        List<Book> allBooks = this.bookStorage.getAllBooks()
        List<BookDisplayElement> returnList = allBooks.stream()
                .map(oneBook -> this.displayElementPrepareService.prepareBookDisplayElement(oneBook))
                .toList()
        return returnList
    }

}
