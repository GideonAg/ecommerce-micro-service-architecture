package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.dto.OrderRequest;
import com.redeemerlives.ecommerce.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    String createOrder(OrderRequest orderRequest);

    List<OrderResponse> findAll();

    OrderResponse findById(Integer orderId);
}
