package com.bookstoreapplication.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
 //Data transfer object Order Model
@Data
public class OrderDTO {

//    @NotEmpty(message="Please provide address")
 private String address;
    private int userId;
//  cartId

   private int price;
}
