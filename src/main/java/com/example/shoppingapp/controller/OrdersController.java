package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.request.PurchaseRequest;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import com.example.shoppingapp.service.OrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersController {

    private final OrderService orderService;

    @Autowired

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }


    // GET     http://localhost:8080/orders/2
    @GetMapping("/orders/{userId}")
    public List<Orders> getOrdersByUserId(@PathVariable int userId) {
        return orderService.getOrdersByUserId(userId);
    }


    //    Test Json format:
    //    POST http://localhost:8080/purchase
    //  {
    //    "productId": 1,
    //        "userId" : 1,
    //        "quantity" : 2
    //  }
    @PostMapping("/purchase")
    public void purchaseProduct(@RequestBody PurchaseRequest purchaseRequest) throws NotEnoughInventoryException {
        orderService.purchaseProduct(purchaseRequest.getProductId(), purchaseRequest.getUserId(), purchaseRequest.getQuantity());
    }



    //PATCH  http://localhost:8080/orders/cancel/2
    @PatchMapping("/orders/cancel/{orderId}")
    public void cancelOrderByOrderId(@PathVariable int orderId) throws NotFoundException {
        orderService.cancelOrderByOrderId(orderId, "canceled");
    }


    // GET  http://localhost:8080/getTop3
    @GetMapping("getTop3")
    public List<Product> getTop3FrequentlyPurchasedItems() {
        return orderService.getTop3FrequentlyPurchasedItems();
    }


    // GET http://localhost:8080/getTop3/2
    @GetMapping("getTop3/{userId}")
    public List<Product> getTop3FrequentlyPurchasedItems(@PathVariable int userId) {
        return orderService.getTop3FrequentlyPurchasedItems(userId);
    }
}
