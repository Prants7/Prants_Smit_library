package com.prants.repository

import com.prants.entity.Reader
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.TypedQuery
import jakarta.transaction.Transactional

@Singleton
class ReaderRepositoryImp implements ReaderRepository {

    private final EntityManager entityManager

    ReaderRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager
    }

    @Override
    @Transactional
    Reader saveNewReader(Reader newReader) {
        entityManager.persist(newReader)
        return newReader
    }

    @Override
    @ReadOnly
    Optional<Reader> getReaderWithReaderCode(String readerCode) {
        TypedQuery<Reader> query = entityManager.createQuery(
                "SELECT r FROM Reader as r WHERE r.readerCode = ?1", Reader.class)
        query.setParameter(1, readerCode)
        try {
            return Optional.ofNullable(query.getSingleResult())
        } catch (NoResultException exception) {
            return Optional.empty()
        }
    }

    @Override
    @ReadOnly
    List<Reader> getAllReaders() {
        String searchString = "SELECT r FROM Reader as r"
        TypedQuery<Reader> query = entityManager.createQuery(searchString, Reader.class)
        return query.getResultList()
    }
}
