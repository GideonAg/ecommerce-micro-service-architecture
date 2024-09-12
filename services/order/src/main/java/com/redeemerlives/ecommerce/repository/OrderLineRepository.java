package com.redeemerlives.ecommerce.repository;

import com.redeemerlives.ecommerce.orderline.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
}
