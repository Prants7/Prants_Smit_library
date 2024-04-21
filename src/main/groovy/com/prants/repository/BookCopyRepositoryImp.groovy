package com.prants.repository

import com.prants.entity.Book
import com.prants.entity.BookCopy
import com.prants.entity.Reader
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
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
        String searchString = "SELECT bc FROM BookCopy as bc WHERE bc.scanCode = ?1"
        TypedQuery<BookCopy> query = entityManager.createQuery(searchString, BookCopy.class)
        query.setParameter(1, scanCode)
        try {
            return Optional.ofNullable(query.getSingleResult())
        } catch (NoResultException exception) {
            return Optional.empty()
        }
    }

    @Override
    @ReadOnly
    Integer getAmountOfTotalCopiesForBook(Book targetBook) {
        String searchString = "SELECT count(bc) FROM BookCopy as bc WHERE bc.bookType = ?1"
        TypedQuery<Integer> query = entityManager.createQuery(searchString, Integer.class)
        query.setParameter(1, targetBook)
        return query.getSingleResult()
    }

    @Override
    @ReadOnly
    List<BookCopy> getAllCopiesForBook(Book targetBook) {
        String searchString = "SELECT bc FROM BookCopy as bc WHERE bc.bookType = ?1"
        TypedQuery<BookCopy> query = entityManager.createQuery(searchString, BookCopy.class)
        query.setParameter(1, targetBook)
        return query.getResultList()
    }
}
