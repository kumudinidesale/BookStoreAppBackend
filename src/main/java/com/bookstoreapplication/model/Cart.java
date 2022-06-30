package com.bookstoreapplication.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
//The @Entity annotation specifies that the class is an entity and is mapped to a database table

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue
    private Integer cartId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserRegistration user;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    private Integer quantity;
    private Integer total;


    public Cart(Integer cartId, Integer quantity, Book book, UserRegistration user,Integer total) {
        super();
        this.cartId = cartId;
        this.quantity = quantity;
        this.book = book;
        this.user = user;
        this.total = total;
    }

    public Cart(Integer quantity, Book book, UserRegistration user,Integer total) {
        super();
        this.quantity = quantity;
        this.book = book;
        this.user = user;
        this.total = total;
    }

    public Cart() {
        super();
    }
}