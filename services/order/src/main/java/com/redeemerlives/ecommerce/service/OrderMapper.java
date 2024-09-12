package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.dto.OrderRequest;
import com.redeemerlives.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .customerId(orderRequest.customerId())
                .totalAmount(orderRequest.amount())
                .reference(orderRequest.reference())
                .paymentMethod(orderRequest.paymentMethod())
                .build();
    }
}
