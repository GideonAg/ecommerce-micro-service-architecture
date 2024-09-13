package com.redeemerlives.ecommerce.service;

import com.redeemerlives.ecommerce.dto.OrderLineRequest;
import com.redeemerlives.ecommerce.dto.OrderLineResponse;
import com.redeemerlives.ecommerce.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        var order = orderLineMapper.toOrderLine(request);
        return orderLineRepository.save(order).getId();
    }

    public List<OrderLineResponse> findOrderLinesByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .toList();
    }
}
