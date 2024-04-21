package com.prants.service

import com.prants.api.display.BorrowDisplayElement
import com.prants.api.forms.BorrowForm
import com.prants.api.forms.ReturnForm
import com.prants.entity.Book
import com.prants.entity.BorrowInstance
import com.prants.repository.BookCopyRepository
import com.prants.repository.BookRepository
import com.prants.repository.BorrowRepository
import com.prants.repository.ReaderRepository
import com.prants.settings.BorrowSettings
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.time.LocalDate

@Singleton
class BorrowService {
    @Inject
    private BookCopyRepository bookCopyRepository
    @Inject
    private ReaderRepository readerRepository
    @Inject
    private BorrowSettings borrowSettings
    @Inject
    private BorrowRepository borrowRepository
    @Inject
    private BookRepository bookRepository
    @Inject
    private DisplayPrepService displayPrepService

    Long saveNewBorrowInstance(BorrowForm newBorrowForm) {
        isFormValid(newBorrowForm)
        BorrowInstance newBorrow = BorrowInstance.newBorrowFromForm(newBorrowForm,
                getExpectedReturnTimeFromToday(), bookCopyRepository, readerRepository)
        BorrowInstance savedBorrow = borrowRepository.saveBorrowInstance(newBorrow)
        return savedBorrow.getId()
    }

    //todo in reality should make it transactional and just cancel if modified amount larger then 1
    void returnBookCopy(ReturnForm newReturnForm) {
        isFormValid(newReturnForm)
        int modifiedInstanceAmount = borrowRepository.returnBookWithScanCode(newReturnForm.getBookScanCode())
        if (modifiedInstanceAmount == 1) {
            System.out.println("Returned book with scan code: " + newReturnForm.getBookScanCode())
        } else {
            System.out.println("Something wrong with book returning, modified amount of lines: " + modifiedInstanceAmount)
        }

    }

    private void isFormValid(BorrowForm newBorrowForm) {
        if (newBorrowForm.getReaderCode() == null) {
            throw new RuntimeException("Form is missing reader code")
        }
        if (newBorrowForm.getBookScanCode() == null) {
            throw new RuntimeException("Form is missing book scan code")
        }
        if (readerRepository.getReaderWithReaderCode(newBorrowForm.getReaderCode()).isEmpty()) {
            throw new RuntimeException("Form has unknown reader code")
        }
        if (bookCopyRepository.findBookCopyWithScanCode(newBorrowForm.getBookScanCode()).isEmpty()) {
            throw new RuntimeException("Form has unknown book scan code")
        }
        if (borrowRepository.getActiveBorrowInstanceForBookCode(newBorrowForm.getBookScanCode()).isPresent()) {
            throw new RuntimeException("Book copy with scan code has already been borrowed out")
        }
    }

    private void isFormValid(ReturnForm newReturnForm) {
        if (newReturnForm.getBookScanCode() == null) {
            throw new RuntimeException("Form is missing book scan code")
        }
        if (bookCopyRepository.findBookCopyWithScanCode(newReturnForm.getBookScanCode()).isEmpty()) {
            throw new RuntimeException("Form has unknown book scan code")
        }
        if (borrowRepository.getActiveBorrowInstanceForBookCode(newReturnForm.getBookScanCode()).isEmpty()) {
            throw new RuntimeException("Book copy with scan code has not been borrowed out")
        }
    }

    LocalDate getExpectedReturnTimeFromToday() {
        LocalDate today = TimeService.getCurrentDate()
        return today.plusDays(borrowSettings.getNormalBorrowDaysAmount())
    }

    List<BorrowDisplayElement> getAllActiveBorrows() {
        List<BorrowInstance> allActiveBorrows = this.borrowRepository.findAllActiveBorrows()
        return allActiveBorrows.stream()
                .map(oneBorrow -> this.displayPrepService.prepareBorrowDisplayElement(oneBorrow))
                .toList()
    }

    List<BorrowDisplayElement> getAllActiveBorrowsForBook(Long bookId) {
        Book targetBook = this.bookRepository.findById(bookId).get()
        List<BorrowInstance> allActiveBorrows = this.borrowRepository.findAllActiveBorrowsForBook(targetBook)
        return allActiveBorrows.stream()
                .map(oneBorrow -> this.displayPrepService.prepareBorrowDisplayElement(oneBorrow))
                .toList()
    }


}
