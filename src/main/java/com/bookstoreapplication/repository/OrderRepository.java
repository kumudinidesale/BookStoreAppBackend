package com.bookstoreapplication.repository;

import com.bookstoreapplication.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderRepository extends JpaRepository<Order,Integer> {
}
