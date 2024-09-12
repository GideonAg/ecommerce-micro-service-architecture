package com.redeemerlives.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull
        Integer productId,
        @Positive(message = "Product quantity must be positive")
        double quantity
) {
}
