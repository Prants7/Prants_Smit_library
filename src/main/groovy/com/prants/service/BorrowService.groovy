package com.prants.service

import com.prants.api.forms.BorrowForm
import com.prants.api.forms.NewBookForm
import com.prants.entity.Book
import com.prants.entity.BorrowInstance
import com.prants.repository.TempBookCopyStorage
import com.prants.repository.TempBorrowStorage
import com.prants.repository.TempReaderStorage
import com.prants.settings.BorrowSettings
import jakarta.inject.Inject
import jakarta.inject.Singleton

import java.time.LocalDate

@Singleton
class BorrowService {
    @Inject
    private TempBorrowStorage borrowStorage
    @Inject
    private TempBookCopyStorage bookCopyStorage
    @Inject
    private TempReaderStorage readerStorage
    @Inject
    private BorrowSettings borrowSettings

    Long saveNewBorrowInstance(BorrowForm newBorrowForm) {
        isFormValid(newBorrowForm)
        BorrowInstance newBorrow = BorrowInstance.newBorrowFromForm(newBorrowForm,
                getExpectedReturnTimeFromToday(), bookCopyStorage, readerStorage)
        BorrowInstance savedBorrow = borrowStorage.saveBorrowInstance(newBorrow)
        return savedBorrow.getId()
    }

    private void isFormValid(BorrowForm newBorrowForm) {
        if (newBorrowForm.getReaderCode() == null) {
            throw new RuntimeException("Form is missing reader code")
        }
        if (newBorrowForm.getBookScanCode() == null) {
            throw new RuntimeException("Form is missing book scan code")
        }
        if (!readerStorage.isReaderCodeInUse(newBorrowForm.getReaderCode())) {
            throw new RuntimeException("Form has unknown reader code")
        }
        if (!bookCopyStorage.isScanCodeInUse(newBorrowForm.getBookScanCode())) {
            throw new RuntimeException("Form has unknown book scan code")
        }
        if (borrowStorage.isBookWithScanCodeAlreadyBorrowedOut(newBorrowForm.getBookScanCode())) {
            throw new RuntimeException("Book copy with scan code has already been borrowed out")
        }
    }

    LocalDate getExpectedReturnTimeFromToday() {
        LocalDate today = TimeService.getCurrentDate()
        return today.plusDays(borrowSettings.getNormalBorrowDaysAmount())
    }
}
