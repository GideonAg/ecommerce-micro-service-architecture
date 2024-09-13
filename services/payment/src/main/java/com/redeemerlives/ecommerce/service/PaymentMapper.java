package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.dto.PaymentRequest;
import com.redeemerlives.ecommerce.payment.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .amount(paymentRequest.amount())
                .orderId(paymentRequest.orderId())
                .paymentMethod(paymentRequest.paymentMethod())
                .build();
    }
}
