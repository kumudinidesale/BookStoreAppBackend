package com.bookstoreapplication.dto;

import com.bookstoreapplication.model.Book;
import lombok.Data;

import java.util.List;
//Data transfer object to display output in message with object format

@Data
public class ResponseDTO {
    private String message;
    private Object data;
    public ResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseDTO() {
    }


    public ResponseDTO(String message) {
        this.message = message;
    }
}