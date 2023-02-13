package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.OrderProduct;
import com.example.shoppingapp.domain.request.ViewOrderRequest;
import com.example.shoppingapp.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderProductController {

    private OrderProductService orderProductService;

    @Autowired
    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }


    // GET  http://localhost:8080/getOrderProductByOrderId
    //    {
    //        "userId" : 2,
    //        "orderId": 3
    //    }
    @GetMapping("/getOrderProductByOrderId")
    public OrderProduct getOrderProductByOrderId(@RequestBody ViewOrderRequest viewOrderRequest) {
        return orderProductService.getOrderProductByOrderId(viewOrderRequest.getUserId(), viewOrderRequest.getOrderId());
    }
}
