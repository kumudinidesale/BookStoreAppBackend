package com.bookstoreapplication.exception;
import lombok.Data;

@Data

public class BookStoreException extends RuntimeException{
    public BookStoreException(String message){
        super(message);//call the constructor of run time ex.
    }

}