package com.msorders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msorders.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
