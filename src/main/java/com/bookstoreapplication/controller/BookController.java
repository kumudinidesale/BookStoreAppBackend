package com.bookstoreapplication.controller;
import com.bookstoreapplication.dto.BookDTO;
import com.bookstoreapplication.dto.ResponseDTO;
import com.bookstoreapplication.model.Book;
import com.bookstoreapplication.model.Cart;
import com.bookstoreapplication.service.BookService;
import com.bookstoreapplication.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
/**
 * In Controller class we write the API's here
 */
@CrossOrigin(allowedHeaders="*",origins="*")
@RestController
@RequestMapping("/book")
 //* Autowired IBookService to inject its dependency here
public class BookController {

    @Autowired
    IBookService bookService;
    // Ability to call api to insert Book record
    // Add Data to repo
    @PostMapping("/insert")
    public ResponseEntity<String> addBookToRepository(@Valid @RequestBody BookDTO bookDTO){
        Book newBook= bookService.createBook(bookDTO);
        ResponseDTO responseDTO=new ResponseDTO("New Book Added in Book Store",newBook);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }
   // Ability to call api to retrieve all book records

    //Get All
    @GetMapping( "/getAll")
    public ResponseEntity<String> getAllBookData()
    {
        List<Book> listOfBooks = bookService.getAllBookData();
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:",listOfBooks);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    @GetMapping(value = "/getcount")
    public ResponseEntity<ResponseDTO> getAddressBookDataCount() {
        List<Book> listOfBooks = bookService.getAllBookData();
        Integer count = listOfBooks.size();
        ResponseDTO dto = new ResponseDTO("Book count successfully (:", count);
        return new ResponseEntity(dto, HttpStatus.OK);
    }
    // Ability to call api to get record by BookId

    //Get All by BookId
    @GetMapping(value = "/getBy/{BookId}")
    public ResponseEntity<String> getBookDataById(@PathVariable Integer BookId)
    {
        Optional<Book> Book = bookService.getBookDataById(BookId);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully by id (:",Book);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    // Ability to call api to delete book record by BookId


    @DeleteMapping("/delete/{BookId}")
    public ResponseEntity<String> deleteRecordById(@PathVariable Integer BookId){
        ResponseDTO dto = new ResponseDTO("Book Record deleted successfully", bookService.deleteRecordById(BookId));
        return new ResponseEntity(dto,HttpStatus.OK);
    }
  // Ability to call api to find book record by BookName

    //find book by Book Name
    @GetMapping("/findbybookname/{bookName}")
    public ResponseEntity<ResponseDTO> getbookByBookName(@PathVariable String bookName) {
        Book book = bookService.getbookByBookName(bookName);
        ResponseDTO response = new ResponseDTO("Search book name is successfully: ", book);
        return new ResponseEntity(response, HttpStatus.OK);
    }
   // Ability to call api to update book record by BookId
    //update record by id
    @PutMapping("/updateBookById/{BookId}")
    public ResponseEntity<String> updateRecordById(@PathVariable Integer BookId,@Valid @RequestBody BookDTO bookDTO){
        Book updateRecord = bookService.updateRecordById(BookId,bookDTO);
        ResponseDTO dto = new ResponseDTO(" Book Record updated successfully by Id",updateRecord);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }

    // to sort book records in ascending order
    @GetMapping("/sortascending")
    public ResponseEntity<String> sortBooksAsc() {
        List<Book> book = bookService.sortBooksAsc();
        ResponseDTO dto = new ResponseDTO("Books in ascending order :", book);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    //to sort book records in descending order
    @GetMapping("/sortdescending")
    public ResponseEntity<String> sortBooksDesc() {
        List<Book> book = bookService.sortBooksDesc();
        ResponseDTO dto = new ResponseDTO("Books in  descending order :", book);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

}