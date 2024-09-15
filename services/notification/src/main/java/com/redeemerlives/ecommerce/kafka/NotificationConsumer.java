package com.redeemerlives.ecommerce.kafka;

import com.redeemerlives.ecommerce.email.EmailService;
import com.redeemerlives.ecommerce.kafka.order.OrderConfirmation;
import com.redeemerlives.ecommerce.kafka.payment.PaymentConfirmation;
import com.redeemerlives.ecommerce.notification.Notification;
import com.redeemerlives.ecommerce.notification.NotificationRepository;
import com.redeemerlives.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentTopic(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming message from payment-topic Topic:: {}", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .paymentConfirmation(paymentConfirmation)
                        .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderTopic(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming message from order-topic Topic:: {}", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .notificationDate(LocalDateTime.now())
                        .notificationType(NotificationType.ORDER_CONFIRMATION)
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customer = orderConfirmation.customer();
        var customerName = customer.firstname() + " " + customer.lastname();
        emailService.sendOrderConfirmationEmail(
                customer.email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }

}
