package com.bookstoreapplication.service;

import com.bookstoreapplication.dto.OrderDTO;
import com.bookstoreapplication.exception.BookStoreException;
import com.bookstoreapplication.model.Book;

import com.bookstoreapplication.model.Cart;
import com.bookstoreapplication.model.Order;
import com.bookstoreapplication.model.UserRegistration;
import com.bookstoreapplication.repository.BookStoreCartRepository;
import com.bookstoreapplication.repository.BookStoreRepository;
import com.bookstoreapplication.repository.OrderRepository;
import com.bookstoreapplication.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
@Slf4j
/**
 * Created OrderService class to serve api calls done by controller layer
 */
public class OrderService implements IOrderService  {
    /**
     * Autowired interface to inject its dependency here
     */
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private BookStoreRepository bookRepo;
    @Autowired
    private UserRegistrationRepository userRepo;
    @Autowired
    private BookStoreCartRepository cartRepo;
/**
 * create a method name as insertOrder
 * Ability to save order details to repository
 * */
    public Order insertOrder(OrderDTO orderdto) {
        Optional<UserRegistration> cart = userRepo.findById(orderdto.getUserId());

        if (cart.isPresent()) {

                Order newOrder = new Order(orderdto);
                orderRepo.save(newOrder);
                log.info("Order record inserted successfully");
                return newOrder;

        } else {
            throw new BookStoreException("Book or User doesn't exists");
        }
    }
/**
 * create a method name as getAllOrderRecords
 * - Ability to get all order data by findAll() method
 * */
    public List<Order> getAllOrderRecords() {
        List<Order> orderList = orderRepo.findAll();
        log.info("ALL order records retrieved successfully");
        return orderList;
    }
/**
 * create a method name as getOrderRecord
 * - Ability to get order data by Id
 * */
    public Order getOrderRecord(Integer id) {
        Optional<Order> order = orderRepo.findById(id);
        if (order.isEmpty()) {
            throw new BookStoreException("Order Record doesn't exists");
        } else {
            log.info("Order record retrieved successfully for id " + id);
            return order.get();
        }
    }
/**
 * create a method name as updateOrderRecord
 * Ability to update order data for particular id
 * */

//    public Order updateOrderRecord(Integer id, OrderDTO dto) {
//        Optional<Order> order = orderRepo.findById(id);
//        Optional<Book> cart = bookRepo.findById(dto.getCartId());
//
//        if (order.isEmpty()) {
//            throw new BookStoreException("Order Record doesn't exists");
//        } else {
//            if (cart.isPresent() ) {
//                if (dto.getQuantity() < book.get().getQuantity()) {
//                    Order newOrder = new Order(id, book.get().getPrice(), dto.getQuantity(), dto.getAddress(), book.get(), user.get(), dto.isCancel());
//                    orderRepo.save(newOrder);
//                    log.info("Order record updated successfully for id " + id);
//                    return newOrder;
//                } else {
//                    throw new BookStoreException("Requested quantity is not available");
//                }
//            } else {
//                throw new BookStoreException("Book or User doesn't exists");
//
//            }
//        }
//    }
/**
 * create a method name as deleteOrderRecord
 * ability to delete data by particular  id
 * */
    public Order deleteOrderRecord(Integer id) {
        Optional<Order> order = orderRepo.findById(id);
        if (order.isEmpty()) {
            throw new BookStoreException("Order Record doesn't exists");
        } else {
            orderRepo.deleteById(id);
            log.info("Order record deleted successfully for id " + id);
            return order.get();
        }
    }
/**
 * create a method name as cancelOrder
 * ability to cnacel data by particular  Id
 * */
    @Override
    public String cancelOrder(int orderId, int userId) {
        Optional<UserRegistration> isPresent = userRepo.findById(userId);
        if (isPresent.isPresent()) {
            Optional<Order> order = orderRepo.findById(orderId);
            if (order.isPresent()) {
                order.get().setCancel(true);
                orderRepo.save(order.get());
                return "Cancel order Successful";
            }
        }
        return "cancel order not successful";
    }
}
