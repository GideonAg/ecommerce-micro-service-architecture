package com.redeemerlives.ecommerce.repository;

import com.redeemerlives.ecommerce.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
