package com.prants.repository

import com.prants.entity.Book
import com.prants.entity.BorrowInstance
import com.prants.service.TimeService
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.TypedQuery
import jakarta.transaction.Transactional

@Singleton
class BorrowRepositoryImp implements BorrowRepository {

    private final EntityManager entityManager

    BorrowRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager
    }

    @Override
    @Transactional
    BorrowInstance saveBorrowInstance(BorrowInstance newBorrowInstance) {
        entityManager.persist(newBorrowInstance)
        return newBorrowInstance
    }

    @Override
    @ReadOnly
    Optional<BorrowInstance> getActiveBorrowInstanceForBookCode(Integer bookCopyScanCode) {
        String searchString = "SELECT bi FROM BorrowInstance as bi WHERE bi.actualReturnDate IS NULL AND bi.borrowedCopy.scanCode = ?1"
        TypedQuery<BorrowInstance> query = entityManager.createQuery(searchString, BorrowInstance.class)
        query.setParameter(1, bookCopyScanCode)
        try {
            return Optional.ofNullable(query.getSingleResult())
        } catch (NoResultException exception) {
            return Optional.empty()
        }
    }

    @Override
    @Transactional
    int returnBookWithScanCode(Integer bookCopyScanCode) {
        return entityManager.createQuery("UPDATE BorrowInstance bi SET bi.actualReturnDate = :returnDate " +
                "where bi.actualReturnDate IS NULL AND bi.borrowedCopy.scanCode = :bookCopyScanCode")
                .setParameter("returnDate", TimeService.getCurrentDate())
                .setParameter("bookCopyScanCode", bookCopyScanCode)
                .executeUpdate()
    }

    @Override
    @ReadOnly
    List<BorrowInstance> findAllActiveBorrows() {
        String searchString = "SELECT bi FROM BorrowInstance as bi WHERE bi.actualReturnDate IS NULL "
        TypedQuery<BorrowInstance> query = entityManager.createQuery(searchString, BorrowInstance.class)
        return query.getResultList()
    }

    @Override
    @ReadOnly
    List<BorrowInstance> findAllActiveBorrowsForBook(Book targetBook) {
        String searchString = "SELECT bi FROM BorrowInstance as bi WHERE bi.actualReturnDate IS NULL AND bi.borrowedCopy.bookType = ?1"
        TypedQuery<BorrowInstance> query = entityManager.createQuery(searchString, BorrowInstance.class)
        query.setParameter(1, targetBook)
        return query.getResultList()
    }

    @Override
    @ReadOnly
    List<BorrowInstance> findAllActiveBorrowsForReader(com.prants.entity.Reader targetReader) {
        String searchString = "SELECT bi FROM BorrowInstance as bi WHERE bi.actualReturnDate IS NULL AND bi.borrower = ?1"
        TypedQuery<BorrowInstance> query = entityManager.createQuery(searchString, BorrowInstance.class)
        query.setParameter(1, targetReader)
        return query.getResultList()
    }

    @Override
    @ReadOnly
    Integer countAllActiveBorrowsForBook(Book targetBook) {
        String searchString = "SELECT count(bi) FROM BorrowInstance as bi WHERE bi.actualReturnDate IS NULL AND bi.borrowedCopy.bookType = ?1"
        TypedQuery<Integer> query = entityManager.createQuery(searchString, Integer.class)
        query.setParameter(1, targetBook)
        return query.getSingleResult()
    }
}
