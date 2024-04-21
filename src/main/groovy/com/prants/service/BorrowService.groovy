package com.prants.service

import com.prants.api.display.BorrowDisplayElement
import com.prants.api.forms.BorrowForm
import com.prants.api.forms.ReturnForm
import com.prants.entity.BorrowInstance
import com.prants.repository.BookCopyRepository
import com.prants.repository.ReaderRepository
import com.prants.repository.TempBorrowStorage
import com.prants.settings.BorrowSettings
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.time.LocalDate

@Singleton
class BorrowService {
    @Inject
    private TempBorrowStorage borrowStorage
    @Inject
    private BookCopyRepository bookCopyRepository
    @Inject
    private ReaderRepository readerRepository
    @Inject
    private BorrowSettings borrowSettings
    @Inject
    private DisplayPrepService displayPrepService

    Long saveNewBorrowInstance(BorrowForm newBorrowForm) {
        isFormValid(newBorrowForm)
        BorrowInstance newBorrow = BorrowInstance.newBorrowFromForm(newBorrowForm,
                getExpectedReturnTimeFromToday(), bookCopyRepository, readerRepository)
        BorrowInstance savedBorrow = borrowStorage.saveBorrowInstance(newBorrow)
        return savedBorrow.getId()
    }

    void returnBookCopy(ReturnForm newReturnForm) {
        isFormValid(newReturnForm)
        BorrowInstance modifiedInstance = borrowStorage.returnBookWithScanCode(newReturnForm.getBookScanCode())
        System.out.println("Returned book in borrow instance: " + modifiedInstance.toString())
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
        if (borrowStorage.isBookWithScanCodeAlreadyBorrowedOut(newBorrowForm.getBookScanCode())) {
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
        if (!borrowStorage.isBookWithScanCodeAlreadyBorrowedOut(newReturnForm.getBookScanCode())) {
            throw new RuntimeException("Book copy with scan code has not been borrowed out")
        }
    }

    LocalDate getExpectedReturnTimeFromToday() {
        LocalDate today = TimeService.getCurrentDate()
        return today.plusDays(borrowSettings.getNormalBorrowDaysAmount())
    }

    List<BorrowDisplayElement> getAllActiveBorrows() {
        List<BorrowInstance> allActiveBorrows = this.borrowStorage.findAllActiveBorrows()
        return allActiveBorrows.stream()
                .map(oneBorrow -> this.displayPrepService.prepareBorrowDisplayElement(oneBorrow))
                .toList()
    }


}
