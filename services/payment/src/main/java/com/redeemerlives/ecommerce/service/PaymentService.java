package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.dto.PaymentConfirmation;
import com.redeemerlives.ecommerce.dto.PaymentRequest;
import com.redeemerlives.ecommerce.kafka.ProducerPayment;
import com.redeemerlives.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ProducerPayment producerPayment;

    public Integer createPayment(PaymentRequest request) {
        var payment = paymentRepository.save(paymentMapper.toPayment(request));

        producerPayment.sendPaymentConfirmation(
                new PaymentConfirmation(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}
