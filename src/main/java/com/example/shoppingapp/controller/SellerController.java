package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.request.SellerAddProductRequest;
import com.example.shoppingapp.domain.request.SellerModifyProductRequest;
import com.example.shoppingapp.domain.request.UpdateProcessingOrderBySeller;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import com.example.shoppingapp.service.SellerService;
import javassist.NotFoundException;
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
//    {
//        "productId": 4,
//            "name":"hat2",
//            "description" : "This is the hat2, we add the stockQuantity to 26",
//            "retailPrice": 35,
//            "wholesalePrice": 20,
//            "stockQuantity" : 26
//    }

    @PatchMapping("seller/modifyProduct/{userId}")
    public void modifyProductsBySellerAndProductId(@PathVariable int userId, @RequestBody SellerModifyProductRequest sellerModifyProductRequest) {
        sellerService.modifyProductsBySellerAndProductId(userId, sellerModifyProductRequest);
    }

    // POST  http://localhost:8080/seller/addProduct/1   (1 means it is a seller)
    @PostMapping("seller/addProduct/{userId}")
    public void addProductBySellerAndProductId(@PathVariable int userId, @RequestBody SellerAddProductRequest sellerAddProductRequest) {
        sellerService.addProductBySellerAndProductId(userId, sellerAddProductRequest);
    }


    // PatchMapping  http://localhost:8080/seller/updateProcessingOrderToCompleteBySellerAndOrderId/1   (1 means it is a seller)
    //    {
    //        "orderId": 4,
    //        "newStatus": "completed"
    //    }
    @PatchMapping("seller/updateProcessingOrderToCompleteBySellerAndOrderId/{userId}")
    public void updateProcessingOrderToCompleteBySellerAndOrderId(@PathVariable int userId, @RequestBody UpdateProcessingOrderBySeller updateProcessingOrderBySeller) throws NotFoundException, InvalidCredentialsException {
        sellerService.updateProcessingOrderToCompleteBySellerAndOrderId(userId, updateProcessingOrderBySeller);
    }


    // PatchMapping  http://localhost:8080/seller/cancelOrderBySellerWithOrderIdAndUserId/1
    //    {
    //        "orderId": 11,
    //        "newStatus": "canceled"
    //    }
    @PatchMapping("seller/cancelOrderBySellerWithOrderIdAndUserId/{userId}")
    public void cancelOrderBySellerWithOrderIdAndUserId(@PathVariable int userId, @RequestBody UpdateProcessingOrderBySeller updateProcessingOrderBySeller) throws NotFoundException, InvalidCredentialsException {
        sellerService.cancelOrderBySellerWithOrderIdAndUserId(userId, updateProcessingOrderBySeller);
    }

}
