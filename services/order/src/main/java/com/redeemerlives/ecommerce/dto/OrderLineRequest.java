package com.redeemerlives.ecommerce.dto;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        double productQuantity
) {
}
