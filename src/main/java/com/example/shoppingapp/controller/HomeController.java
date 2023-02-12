package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.domain.request.PurchaseRequest;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import com.example.shoppingapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @Autowired


    @GetMapping("/product")
    public List<Product> viewAllValidProducts() {
        return productService.viewAllValidProducts();
    }


    @GetMapping("/product/{productId}")
    public Product getProductById(@PathVariable int productId) {
        return productService.getProductById(productId);
    }


    @GetMapping("/orders/{userId}")
    public List<Orders> getOrdersByUserId(int userId) {
        return productService.getOrdersByUserId(userId);
    }


//    Test Json format:
//  {
//    "productId": 1,
//        "userId" : 1,
//        "quantity" : 2
//  }
    @PostMapping("/purchase")
    public void purchaseProduct(@RequestBody PurchaseRequest purchaseRequest) throws NotEnoughInventoryException {
        productService.purchaseProduct(purchaseRequest.getProductId(), purchaseRequest.getUserId(), purchaseRequest.getQuantity());
    }

}