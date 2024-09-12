package com.redeemerlives.ecommerce.kafka;

import com.redeemerlives.ecommerce.dto.CustomerResponse;
import com.redeemerlives.ecommerce.dto.PurchaseResponse;
import com.redeemerlives.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
