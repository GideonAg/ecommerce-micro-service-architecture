package com.redeemerlives.ecommerce.dto;

import com.redeemerlives.ecommerce.order.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,

        @Positive(message = "Order amount should be positive")
        BigDecimal amount,

        @NotNull(message = "Valid payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Customer should be present")
        @NotBlank(message = "Valid customer is required")
        String customerId,

        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products
) {
}
