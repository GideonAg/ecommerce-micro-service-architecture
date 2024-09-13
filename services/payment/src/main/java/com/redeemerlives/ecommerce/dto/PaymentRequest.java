package com.redeemerlives.ecommerce.dto;

import com.redeemerlives.ecommerce.payment.Customer;
import com.redeemerlives.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        String orderReference,
        PaymentMethod paymentMethod,
        Integer orderId,
        Customer customer
) {
}
