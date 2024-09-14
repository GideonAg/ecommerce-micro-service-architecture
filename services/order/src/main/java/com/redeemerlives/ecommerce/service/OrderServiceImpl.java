package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.clients.CustomerClient;
import com.redeemerlives.ecommerce.clients.PaymentClient;
import com.redeemerlives.ecommerce.clients.ProductClient;
import com.redeemerlives.ecommerce.dto.*;
import com.redeemerlives.ecommerce.exception.BusinessException;
import com.redeemerlives.ecommerce.kafka.OrderConfirmation;
import com.redeemerlives.ecommerce.kafka.OrderProducer;
import com.redeemerlives.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;
    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;

    @Override
    public String createOrder(OrderRequest orderRequest) {
        CustomerResponse customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() ->
                        new BusinessException("Cannot create order:: No customer exist with the provided ID")
                );
        List<PurchaseResponse> purchasedProduct = this.productClient.purchaseProducts(orderRequest.products());

        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

        var products = orderRequest.products();
        for (var product : products) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            product.productId(),
                            product.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.reference(),
                orderRequest.paymentMethod(),
                order.getId(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProduct
                )
        );
        return "Order placed successfully";
    }

    @Override
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException("No order found with ID:: " + orderId));
    }
}
