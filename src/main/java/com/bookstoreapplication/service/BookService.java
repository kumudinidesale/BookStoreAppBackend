package com.bookstoreapplication.service;

import com.bookstoreapplication.dto.BookDTO;
import com.bookstoreapplication.exception.BookStoreException;
import com.bookstoreapplication.model.Book;
import com.bookstoreapplication.repository.BookStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
/**
 * Created BookService class to serve api calls done by controller layer
 */
public class BookService implements IBookService{
    /**
     * Autowired BookStoreRepository interface to inject its dependency here
     */
    @Autowired
    BookStoreRepository bookStoreRepository;
   /* create a method name as createBook
   Ability to save book details to repository
   */

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book newBook = new Book(bookDTO);
        return  bookStoreRepository.save(newBook);

    }
/**
 * create a method name as getBookDataById
 * - Ability to get book data by id
 */
    @Override
    public Optional<Book> getBookDataById(int BookId) {
        Optional<Book> getBook=bookStoreRepository.findById(BookId);
        if(getBook.isPresent()){
            return getBook;

        }
        throw new BookStoreException("Book Store Details for id not found");

    }
/**
 * create a method name as getAllBookData
 * - Ability to get all book' data by findAll() method
 */
    @Override
    public List<Book> getAllBookData() {
        List<Book> getBooks=bookStoreRepository.findAll();
        return getBooks;
    }
/**
 * create a method name as updateRecordById
 * Ability to update book data for particular id
 * */
    @Override
    public Book updateRecordById(Integer BookId, BookDTO bookDTO) {

        Optional<Book> updateBook = bookStoreRepository.findById(BookId);
        if (updateBook.isPresent()) {
            Book updateUser = new Book(BookId, bookDTO);
            bookStoreRepository.save(updateUser);
            return updateUser;

        } else {

            throw new BookStoreException("Book record does not found");
        }
    }

    /**
     * create a method name as getbookByBookName
     * ability to get data by particular book Name
     * */
    @Override
    public Book getbookByBookName(String bookName) {
        Optional<Book> book = bookStoreRepository.findByBookName(bookName);
        if(book.isEmpty()) {
            throw new BookStoreException("There are no Books with given name");
        }
        return book.get();
    }

    /**
 * create a method name as deleteRecordById
 * ability to delete data by particular book id
 * */
    @Override
    public String deleteRecordById(int BookId) {
        Optional<Book> newBook = bookStoreRepository.findById(BookId);
        if (newBook.isPresent()) {
            bookStoreRepository.deleteById(BookId);

        } else {
            throw new BookStoreException("Book record does not found");
        }
        return "data deleted succesfull";
    }
//    //list of book in ascending order
    @Override
    public List<Book> sortBooksAsc() {
        return  bookStoreRepository.sortBooksAsc();
    }

    //list of book in descending order
    @Override
    public List<Book> sortBooksDesc() {
        return bookStoreRepository.sortBooksDesc();
    }



}