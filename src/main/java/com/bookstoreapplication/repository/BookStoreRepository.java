package com.bookstoreapplication.repository;

import com.bookstoreapplication.model.Book;
import com.bookstoreapplication.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//Ability to provide CRUD operations and create table for given entity

@Repository
public interface BookStoreRepository extends JpaRepository<Book,Integer> {

    @Query(value = "SELECT * from book WHERE book_name= :bookName", nativeQuery = true)
    Optional<Book> findByBookName(String bookName);

    @Query(value = "SELECT * from book ORDER BY price", nativeQuery = true)
    List<Book> sortBooksDesc();

    @Query(value = "SELECT * from book ORDER BY price DESC", nativeQuery = true)
    List<Book> sortBooksAsc();

}