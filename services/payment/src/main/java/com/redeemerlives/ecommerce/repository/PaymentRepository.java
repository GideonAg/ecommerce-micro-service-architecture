package com.redeemerlives.ecommerce.repository;

import com.redeemerlives.ecommerce.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
