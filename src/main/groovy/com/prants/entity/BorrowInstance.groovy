package com.prants.entity

import com.prants.api.forms.BorrowForm
import com.prants.repository.BookCopyRepository
import com.prants.repository.ReaderRepository
import com.prants.service.TimeService

import java.time.LocalDate

class BorrowInstance {
    private Long id
    private BookCopy borrowedCopy
    private Reader borrower
    private LocalDate dayWhenBorrowed
    private LocalDate designatedReturnDate
    private LocalDate actualReturnDate

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    BookCopy getBorrowedCopy() {
        return borrowedCopy
    }

    void setBorrowedCopy(BookCopy borrowedCopy) {
        this.borrowedCopy = borrowedCopy
    }

    Reader getBorrower() {
        return borrower
    }

    void setBorrower(Reader borrower) {
        this.borrower = borrower
    }

    LocalDate getDayWhenBorrowed() {
        return dayWhenBorrowed
    }

    void setDayWhenBorrowed(LocalDate dayWhenBorrowed) {
        this.dayWhenBorrowed = dayWhenBorrowed
    }

    LocalDate getDesignatedReturnDate() {
        return designatedReturnDate
    }

    void setDesignatedReturnDate(LocalDate designatedReturnDate) {
        this.designatedReturnDate = designatedReturnDate
    }

    LocalDate getActualReturnDate() {
        return actualReturnDate
    }

    void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate
    }

    static BorrowInstance newBorrowFromForm(BorrowForm borrowForm,
                                            LocalDate expectedReturnDate,
                                            BookCopyRepository bookCopyRepository,
                                            ReaderRepository readerRepository) {
        BorrowInstance newBorrow = new BorrowInstance()
        newBorrow.setBorrowedCopy(bookCopyRepository.findBookCopyWithScanCode(borrowForm.getBookScanCode()).orElseThrow())
        newBorrow.setBorrower(readerRepository.getReaderWithReaderCode(borrowForm.getReaderCode()).orElseThrow())
        newBorrow.setDayWhenBorrowed(TimeService.getCurrentDate())
        newBorrow.setDesignatedReturnDate(expectedReturnDate)
        return newBorrow
    }


    @Override
    String toString() {
        return "BorrowInstance{" +
                "id=" + id +
                ", borrowedCopy=" + borrowedCopy +
                ", borrower=" + borrower +
                ", dayWhenBorrowed=" + dayWhenBorrowed +
                ", designatedReturnDate=" + designatedReturnDate +
                ", actualReturnDate=" + actualReturnDate +
                '}'
    }
}
