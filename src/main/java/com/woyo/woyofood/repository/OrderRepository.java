package com.woyo.woyofood.repository;

import com.woyo.woyofood.model.order.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
}
