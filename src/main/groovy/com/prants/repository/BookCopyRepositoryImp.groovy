package com.prants.repository

import com.prants.entity.Book
import com.prants.entity.BookCopy
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import jakarta.transaction.Transactional

@Singleton
class BookCopyRepositoryImp implements BookCopyRepository {

    private final EntityManager entityManager

    BookCopyRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager
    }

    @Override
    @Transactional
    BookCopy saveNewBookCopy(BookCopy newBookCopy) {
        entityManager.persist(newBookCopy)
        return newBookCopy
    }

    @Override
    @ReadOnly
    Optional<BookCopy> findBookCopyWithId(Long bookCopyId) {
        return Optional.ofNullable(entityManager.find(BookCopy, bookCopyId))
    }

    @Override
    @ReadOnly
    Optional<BookCopy> findBookCopyWithScanCode(Integer scanCode) {
        String searchString = "SELECT bc FROM book_copy as bc WHERE bc.scanCode =" + scanCode
        TypedQuery<BookCopy> query = entityManager.createQuery(searchString, BookCopy.class)
        return Optional.ofNullable(query.getSingleResult())
    }

    @Override
    @ReadOnly
    Integer getAmountOfTotalCopiesForBook(Book targetBook) {
        String searchString = "SELECT count(bc) FROM book_copy bc WHERE bc.book_type =" + targetBook.id
        Query query = entityManager.createNativeQuery(searchString, Integer.class)
        return query.getSingleResult()
    }

    @Override
    @ReadOnly
    Integer getAmountOfAvailableCopiesForBook(Book targetBook) {
        //todo replace when we have borrow forms in db
        return getAmountOfTotalCopiesForBook(targetBook)
    }
}
