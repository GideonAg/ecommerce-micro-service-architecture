package com.redeemerlives.ecommerce.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product name is required")
        @NotBlank(message = "Product name is required")
        String name,

        @NotBlank(message = "Product description is required")
        @NotNull(message = "Product description is required")
        String description,

        @NotBlank(message = "Product quantity is required")
        @Positive(message = "Product available quantity should be positive")
        double availableQuantity,

        @Positive(message = "Price should be positive")
        BigDecimal price,

        @NotNull(message = "Product category is required")
        Integer categoryId
) {
}
