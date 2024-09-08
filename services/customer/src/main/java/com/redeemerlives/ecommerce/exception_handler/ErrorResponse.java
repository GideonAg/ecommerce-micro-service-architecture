package com.redeemerlives.ecommerce.exception_handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
