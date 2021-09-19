package com.woyo.woyofood.service;

import com.woyo.woyofood.model.order.OrderModel;
import com.woyo.woyofood.repository.OrderRepository;
import com.woyo.woyofood.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderDto getOrderDetail(int orderId) {

        Optional<OrderModel> orderExists = orderRepository.findById(orderId);

        if (orderExists.isPresent()) {
            OrderDto orderDto = new OrderDto();

            orderDto.setOrderId(orderExists.get().getOrderId());
            orderDto.setUserId(orderExists.get().getUserId());
            orderDto.setFoodId(orderExists.get().getFoodId());
            orderDto.setQty(orderExists.get().getQty());
            orderDto.setTotalPrice(orderExists.get().getTotalPrice());
            orderDto.setStatus(orderExists.get().getStatus());
            orderDto.setAddress(orderExists.get().getAddress());

            return orderDto;
        } else {
            return null;
        }
    }
}
