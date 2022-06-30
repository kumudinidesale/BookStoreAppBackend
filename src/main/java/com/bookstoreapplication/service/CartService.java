package com.bookstoreapplication.service;

import com.bookstoreapplication.dto.CartDTO;
import com.bookstoreapplication.dto.ResponseDTO;
import com.bookstoreapplication.exception.BookStoreException;
import com.bookstoreapplication.model.Book;
import com.bookstoreapplication.model.Cart;
import com.bookstoreapplication.model.UserRegistration;
import com.bookstoreapplication.repository.BookStoreCartRepository;
import com.bookstoreapplication.repository.BookStoreRepository;
import com.bookstoreapplication.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
/**
 * Created CartService class to serve api calls done by controller layer
 */
public class CartService implements ICartService{
    /**
     * Autowired interfaces to inject its dependency here
     */
    @Autowired
    BookStoreRepository bookStoreRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    BookStoreCartRepository bookStoreCartRepository;

/**
 * create a method name as insertItems
 * Ability to save cart details to repository
 * */
    @Override
    public Cart insertItems(CartDTO cartdto) {
        Optional<Book> book = bookStoreRepository.findById(cartdto.getBookId());
        Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(cartdto.getUserId());
        if (book.isPresent() && userRegistration.isPresent()) {
            Cart newCart = new Cart(cartdto.getQuantity(), book.get(), userRegistration.get(), cartdto.getTotal());
            bookStoreCartRepository.save(newCart);
            return newCart;
        } else {
            throw new BookStoreException("Book or User does not exists");
        }
    }
/**
 * create a method name as getCartDetails
 * - Ability to get all cart' data by findAll() method
 * */
    @Override
    public ResponseDTO getCartDetails() {
        List<Cart> getCartDetails=bookStoreCartRepository.findAll();
        ResponseDTO dto= new ResponseDTO();
        if (getCartDetails.isEmpty()){
            String   message=" Not found Any Cart details ";
            dto.setMessage(message);
            dto.setData(0);
            return dto;

        }
        else {
            dto.setMessage("the list of cart items is sucussfully retrived");
            dto.setData(getCartDetails);
            return dto;
        }
    }
/**
 * create a method name as getCartDetailsById
 * - Ability to get cart data by cartId*/

    @Override
    public Optional<Cart> getCartDetailsById(Integer cartId) {
        Optional<Cart> getCartData=bookStoreCartRepository.findById(cartId);
        if (getCartData.isPresent()){
            return getCartData;
        }
        else {
            throw new BookStoreException(" Didn't find any record for this particular cartId");
        }
    }
/**
 * create a method name as getCartRecordByBookId
 * - Ability to get cart data by bookId
  */
    public Cart getCartRecordByBookId(Integer bookId) {
        Optional<Cart> cart = bookStoreCartRepository.findByBookId(bookId);
        if(cart.isPresent()) {
            log.info("Cart record retrieved successfully for book id "+bookId);
            return cart.get();

        }
        else {
            return null;
            //throw new BookStoreException("Cart Record doesn't exists");
        }
    }

    @Override
    public Cart updateQuntity(Integer cartId, int quantity,int total) {
        Optional<Cart> cartData=bookStoreCartRepository.findById(cartId);
        if (cartData.isEmpty()){
            throw new BookStoreException("invalid id please input valid Id");
        }
        cartData.get().setQuantity(quantity);
        cartData.get().setTotal(total);
        bookStoreCartRepository.save(cartData.get());
        return cartData.get();
    }

    /**
 * create a method name as deleteCartItemById
 * ability to delete data by particular cart id
 * */
    @Override
    public Optional<Cart> deleteCartItemById(Integer cartId) {
        Optional<Cart> deleteData=bookStoreCartRepository.findById(cartId);
        if (deleteData.isPresent()){
            bookStoreCartRepository.deleteById(cartId);
            return deleteData;
        }
        else {
            throw new BookStoreException(" Did not get any cart for specific cart id ");
        }

    }



    @Override
    public void deleteAllCartItem() {
         bookStoreCartRepository.deleteAll();

    }


/**
 * create a method name as updateRecordById
 * Ability to update cart data for particular id
 * */
    @Override
    public Cart updateRecordById(Integer cartId, CartDTO cartDTO) {
        Optional<Cart> cart = bookStoreCartRepository.findById(cartId);
        Optional<Book>  book = bookStoreRepository.findById(cartDTO.getBookId());
        Optional<UserRegistration> user = userRegistrationRepository.findById(cartDTO.getUserId());
        if(cart.isPresent()) {
            if(book.isPresent() && user.isPresent()) {
                Cart newCart = new Cart(cartId,cartDTO.getQuantity(),book.get(),user.get(), cartDTO.getTotal());
                bookStoreCartRepository.save(newCart);
                log.info("Cart record updated successfully for id "+cartId);
                return newCart;
            }
            else {
                throw new BookStoreException("Book or User doesn't exists");
            }
        }
        else {
            throw new BookStoreException("Cart Record doesn't exists");
        }
    }

    }


