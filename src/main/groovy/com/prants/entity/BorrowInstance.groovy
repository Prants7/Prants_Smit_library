package com.prants.entity

import java.time.LocalDate

class BorrowInstance {
    private Long id
    private BookCopy borrowedCopy
    private Reader borrower
    private LocalDate dayWhenBorrowed
    private LocalDate designatedReturnDate
    private LocalDate actualReturnDate
}
