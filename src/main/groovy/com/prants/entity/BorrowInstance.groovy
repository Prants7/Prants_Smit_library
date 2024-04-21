package com.prants.entity

import com.prants.api.forms.BorrowForm
import com.prants.repository.BookCopyRepository
import com.prants.repository.ReaderRepository
import com.prants.service.TimeService
import groovy.transform.CompileStatic
import jakarta.annotation.Nonnull
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

import java.time.LocalDate

@CompileStatic
@Entity
@Table(name = 'borrow_instance')
@SequenceGenerator(name="borrow_instance_seq", allocationSize=1)
class BorrowInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrow_instance_seq")
    private Long id
    @Nonnull
    @ManyToOne
    @JoinColumn(name = "borrowed_copy")
    private BookCopy borrowedCopy
    @Nonnull
    @ManyToOne
    @JoinColumn(name = "borrower")
    private Reader borrower
    @Column(name = "day_when_borrowed", nullable = false)
    private LocalDate dayWhenBorrowed
    @Column(name = "designated_return_date", nullable = false)
    private LocalDate designatedReturnDate
    @Column(name = "actual_return_date")
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

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        BorrowInstance that = (BorrowInstance) o

        if (id != that.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
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
