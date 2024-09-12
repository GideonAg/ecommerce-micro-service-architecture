package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.dto.OrderRequest;

public interface OrderService {
    String createOrder(OrderRequest orderRequest);
}
