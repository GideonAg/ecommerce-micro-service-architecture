package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.dto.OrderLineRequest;
import com.redeemerlives.ecommerce.dto.OrderLineResponse;
import com.redeemerlives.ecommerce.order.Order;
import com.redeemerlives.ecommerce.orderline.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .order(Order.builder()
                        .id(request.orderId())
                        .build())
                .productId(request.productId())
                .quantity(request.productQuantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
