package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.request.SellerAddProductRequest;
import com.example.shoppingapp.domain.request.SellerModifyProductRequest;
import com.example.shoppingapp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    @Autowired
    private SellerService sellerService;


    // GET  http://localhost:8080/seller/orders/1   (1 means it is a seller)
    @GetMapping("seller/orders/{userId}")
    public List<Orders> getAllOrdersBySeller(@PathVariable int userId) {
        return sellerService.getAllOrdersBySeller(userId);
    }


    // GET  http://localhost:8080/seller/products/1   (1 means it is a seller)
    @GetMapping("seller/products/{userId}")
    public List<Product> getAllProductsBySeller(@PathVariable int userId) {
        return sellerService.getAllProductsBySeller(userId);
    }


    // PatchMapping  http://localhost:8080/seller/modifyProduct/1   (1 means it is a seller)
    @PatchMapping("seller/modifyProduct/{userId}")
    public void modifyProductsBySellerAndProductId(@PathVariable int userId, @RequestBody SellerModifyProductRequest sellerModifyProductRequest) {
        sellerService.modifyProductsBySellerAndProductId(userId, sellerModifyProductRequest);
    }

    // POST  http://localhost:8080/seller/addProduct/1   (1 means it is a seller)
    @PostMapping("seller/addProduct/{userId}")
    public void addProductBySellerAndProductId(@PathVariable int userId, @RequestBody SellerAddProductRequest sellerAddProductRequest) {
        sellerService.addProductBySellerAndProductId(userId, sellerAddProductRequest);
    }


}
