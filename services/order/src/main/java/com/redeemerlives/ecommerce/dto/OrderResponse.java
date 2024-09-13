package com.redeemerlives.ecommerce.dto;

import com.redeemerlives.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
    Integer id,
    String reference,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String customerId
) {
}
