package com.bookstoreapplication.model;

import com.bookstoreapplication.dto.OrderDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
 //The @Entity annotation specifies that the class is an entity and is mapped to a database table

@Entity
@Data
@Table(name="orderDetails")
public class Order {
    @Id
    @GeneratedValue
    private Integer orderID;
    private LocalDate date = LocalDate.now();
   private Integer price;
//    private Integer quantity;
    private String address;
    private int userId;
//    @ManyToOne
//    @JoinColumn(name="cartId")
//    private Cart cart;
//    @ManyToOne
//    @JoinColumn(name="bookId")
//    private Book book;
    private boolean cancel;



    public Order() {
        super();
    }




    public  Order(OrderDTO orderDto) {
        this.date = LocalDate.now();
        this.price = orderDto.getPrice();
        this.userId = orderDto.getUserId();
        this.cancel = false;

    }
}
