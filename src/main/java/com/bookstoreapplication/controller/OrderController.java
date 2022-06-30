package com.bookstoreapplication.controller;

import com.bookstoreapplication.dto.OrderDTO;
import com.bookstoreapplication.dto.ResponseDTO;
import com.bookstoreapplication.model.Order;
import com.bookstoreapplication.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
/**
 * In Controller class we write the API's here
 */

@RestController
@RequestMapping("/order")
@CrossOrigin(allowedHeaders="*",origins="*")
public class OrderController  {
    /**
     * Autowired IOrderService dependency to inject its dependecy here
     */
    @Autowired
    private IOrderService orderService;
    //Ability to call api to insert order record
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertOrder(@Valid @RequestBody OrderDTO orderdto){
        Order newOrder = orderService.insertOrder(orderdto);
        ResponseDTO dto = new ResponseDTO("Order place successfully !",newOrder);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }
   // Ability to call api retrieve all order records
    @GetMapping("/retrieveAllOrders")
    public ResponseEntity<ResponseDTO> getAllOrderRecords(){
        List<Order> newOrder = orderService.getAllOrderRecords();
        ResponseDTO dto = new ResponseDTO("All records retrieved successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    //Ability to call api to retrieve order records by id
    @GetMapping("/retrieveOrder/{id}")
    public ResponseEntity<ResponseDTO> getBookRecord(@PathVariable Integer id){
        Order newOrder = orderService.getOrderRecord(id);
        ResponseDTO dto = new ResponseDTO("Record retrieved successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.OK);
    }
    //Ability to call api to update order record by id
//    @PutMapping("/updateOrder/{id}")
//    public ResponseEntity<ResponseDTO> updateBookRecord(@PathVariable Integer id,@Valid @RequestBody OrderDTO orderdto){
//        Order newOrder = orderService.updateOrderRecord(id,orderdto);
//        ResponseDTO dto = new ResponseDTO("Record updated successfully !",newOrder);
//        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
//    }
   // Ability to call api to delete order record by id
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<ResponseDTO> deleteOrderRecord(@PathVariable Integer id){
        Order newOrder = orderService.deleteOrderRecord(id);
        ResponseDTO dto = new ResponseDTO("Record deleted successfully !",newOrder);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
    //Ability to call api to cancel order record by id
    @PutMapping("/cancelOrder/{orderId}/{userId}")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable int orderId,@PathVariable int userId) {
        String order=orderService.cancelOrder(orderId,userId);
        ResponseDTO response= new ResponseDTO("id "+orderId,  order);
        return new ResponseEntity<> (response,HttpStatus.ACCEPTED);
    }
}
