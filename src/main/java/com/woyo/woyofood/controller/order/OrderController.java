package com.woyo.woyofood.controller.order;

import com.woyo.woyofood.response.DataResponse;
import com.woyo.woyofood.response.HandlerResponse;
import com.woyo.woyofood.service.OrderService;
import com.woyo.woyofood.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/order", produces = {"application/json"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public void getDetailOrder(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable("orderId") int orderId) throws IOException {

        OrderDto orderDto = orderService.getOrderDetail(orderId);

        if (orderDto != null) {
            DataResponse<OrderDto> dataResponse = new DataResponse<>();
            dataResponse.setData(orderDto);
            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "Order not found");
        }
    }
}
