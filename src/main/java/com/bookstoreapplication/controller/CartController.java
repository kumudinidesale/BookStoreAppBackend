package com.bookstoreapplication.controller;

import com.bookstoreapplication.dto.CartDTO;
import com.bookstoreapplication.dto.ResponseDTO;
import com.bookstoreapplication.model.Cart;
import com.bookstoreapplication.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;
@CrossOrigin(allowedHeaders="*",origins="*")
@RestController
@RequestMapping("/cart")
/**
 * In Controller class we write the API's here
 */
public class CartController {
    /**
     * Autowired ICartService to inject its dependency here
     */
    @Autowired
    ICartService cartService;
    //Ability to call api to insert all cart records
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> insertItem(@Valid @RequestBody CartDTO cartdto) {
        Cart newCart = cartService.insertItems(cartdto);
        ResponseDTO responseDTO = new ResponseDTO("User registered successfully !", newCart);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }
    //Ability to call api to retrieve all cart records
    @GetMapping("/getAll")
    public ResponseDTO getAllCartDetails() {
        ResponseDTO responseDTO = cartService.getCartDetails();
        return responseDTO;
    }
    //Ability to call api to retrieve cart record by cartId
    @GetMapping("/getById/{cartId}")
    public ResponseEntity<ResponseDTO> getCartDetailsById(@PathVariable Integer cartId){
        Optional<Cart> specificCartDetail=cartService.getCartDetailsById(cartId);
        ResponseDTO responseDTO=new ResponseDTO("Cart details retrieved successfully",specificCartDetail);
        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
    }
    //Ability to call api to retrieve cart record by book id
    @GetMapping("/retrieveCartByBookId/{bookID}")
    public ResponseEntity<ResponseDTO> getCartRecordByBookId(@PathVariable Integer bookID){
        Cart newCart = cartService.getCartRecordByBookId(bookID);
        ResponseDTO dto = new ResponseDTO("Record retrieved successfully !",newCart);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    //Ability to call api to delete cart by id
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCartById(@PathVariable Integer cartId) {
        Optional<Cart> delete = cartService.deleteCartItemById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart delete successfully", delete);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<ResponseDTO> deleteAllCart() {
        cartService.deleteAllCartItem();
        ResponseDTO responseDTO = new ResponseDTO("Cart delete successfully");
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    //Ability to call api to update cart by id
    @PutMapping("/updateById/{cartId}")
    public ResponseEntity<ResponseDTO> updateCartById(@PathVariable Integer cartId,@Valid @RequestBody CartDTO cartDTO){
        Cart updateRecord = cartService.updateRecordById(cartId,cartDTO);
        ResponseDTO dto = new ResponseDTO(" Cart Record updated successfully by Id",updateRecord);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
    @PutMapping("/UpdateQunatity/{cartId}/{quantity}/{total}")
    public ResponseEntity<ResponseDTO> updateQuntityData(@PathVariable Integer cartId, @PathVariable int quantity,@PathVariable int total) {
        Cart updateQuntity = cartService.updateQuntity(cartId, quantity,total);
        ResponseDTO dto = new ResponseDTO("Cart quntity update Successfully " ,updateQuntity);
        return  new ResponseEntity<>(dto,HttpStatus.ACCEPTED);
    }


}