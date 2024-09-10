package com.redeemerlives.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product is required")
        Integer productId,

        @NotNull(message = "Product quantity is required")
        @Positive(message = "Purchase quantity should be positive")
        double quantity
) {
}
