package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.service.OrderService;
import com.example.shoppingapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET http://localhost:8080/product
    @GetMapping("/product")
    public List<Product> viewAllValidProducts() {
        return productService.viewAllValidProducts();
    }


    // GET     http://localhost:8080/product/1
    @GetMapping("/product/{productId}")
    public Product getProductById(@PathVariable int productId) {
        return productService.getProductById(productId);
    }




}