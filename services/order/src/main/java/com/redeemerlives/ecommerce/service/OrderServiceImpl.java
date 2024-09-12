package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.clients.CustomerClient;
import com.redeemerlives.ecommerce.clients.ProductClient;
import com.redeemerlives.ecommerce.dto.CustomerResponse;
import com.redeemerlives.ecommerce.dto.OrderLineRequest;
import com.redeemerlives.ecommerce.dto.OrderRequest;
import com.redeemerlives.ecommerce.dto.PurchaseResponse;
import com.redeemerlives.ecommerce.exception.BusinessException;
import com.redeemerlives.ecommerce.kafka.OrderConfirmation;
import com.redeemerlives.ecommerce.kafka.OrderProducer;
import com.redeemerlives.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final CustomerClient customerClient;
    private final ProductClient productClient;
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

        // todo start payment process

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
}
