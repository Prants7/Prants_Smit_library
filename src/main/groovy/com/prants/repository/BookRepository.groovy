package com.prants.repository

import com.prants.entity.Book

interface BookRepository {

    Optional<Book> findById(long id)

    Book saveNewBook(Book newBook)

    List<Book> findAllBooks()

}