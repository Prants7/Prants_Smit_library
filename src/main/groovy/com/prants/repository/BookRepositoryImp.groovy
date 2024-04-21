package com.prants.repository

import com.prants.entity.Book
import io.micronaut.transaction.annotation.ReadOnly
import jakarta.inject.Singleton
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import jakarta.transaction.Transactional

@Singleton
class BookRepositoryImp implements BookRepository {

    private final EntityManager entityManager

    BookRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager
    }


    @Override
    @ReadOnly
    Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book, id))
    }

    @Override
    @Transactional
    Book saveNewBook(Book newBook) {
        entityManager.persist(newBook)
        return newBook
    }

    @Override
    @ReadOnly
    List<Book> findAllBooks() {
        String searchString = "SELECT b FROM Book as b"
        TypedQuery<Book> query = entityManager.createQuery(searchString, Book.class)
        return query.getResultList()
    }
}
