package com.prants.repository

import com.prants.entity.Book
import com.prants.entity.BorrowInstance

interface BorrowRepository {

    BorrowInstance saveBorrowInstance(BorrowInstance newBorrowInstance)

    Optional<BorrowInstance> getActiveBorrowInstanceForBookCode(Integer bookCopyScanCode)

    int returnBookWithScanCode(Integer bookCopyScanCode)

    List<BorrowInstance> findAllActiveBorrows()

    List<BorrowInstance> findAllActiveBorrowsForBook(Book targetBook)

    Integer countAllActiveBorrowsForBook(Book targetBook)

}