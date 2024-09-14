package com.redeemerlives.ecommerce.dto;

import com.redeemerlives.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        String orderReference,
        PaymentMethod paymentMethod,
        Integer orderId,
        CustomerResponse customer
) {
}
