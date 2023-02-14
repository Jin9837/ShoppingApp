package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.domain.request.SellerAddProductRequest;
import com.example.shoppingapp.domain.request.SellerModifyProductRequest;
import com.example.shoppingapp.domain.request.UpdateProcessingOrderBySeller;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import com.example.shoppingapp.service.OrderProductService;
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


    // GET  http://localhost:8080/seller/getOrdersBySellerAndOrderId?userId=1&orderId=2
    @GetMapping("seller/getOrdersBySellerAndOrderId")
    public Orders getOrdersBySellerAndOrderId(@RequestParam int userId, @RequestParam int orderId) {
        return sellerService.getOrdersBySellerAndOrderId(userId, orderId);
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



    // GET  http://localhost:8080/seller/getMostProfitableProductsBySeller/1
    @GetMapping("seller/getMostProfitableProductsBySeller/{userId}")
    public List<Product> getMostProfitableProductsBySeller(@PathVariable int userId) throws InvalidCredentialsException {
        return sellerService.getMostProfitableProductsBySeller(userId);
    }


    // GET  http://localhost:8080/seller/getTop3FrequentlyPurchasedProducts/1
    @GetMapping("seller/getTop3FrequentlyPurchasedProducts/{userId}")
    public List<Product> getTop3FrequentlyPurchasedProducts(@PathVariable int userId) throws InvalidCredentialsException {
        return sellerService.getTop3FrequentlyPurchasedProducts(userId);
    }


    // GET  http://localhost:8080/seller/getTotalSoldItemsBySeller/1
    @GetMapping("seller/getTotalSoldItemsBySeller/{userId}")
    public int getTotalSoldItemsBySeller(@PathVariable int userId) {
        return sellerService.getTotalSoldItemsBySeller(userId);
    }


    // GET  http://localhost:8080/seller/getTop3UsersByTotalPurchaseAmount/1
    @GetMapping("seller/getTop3UsersByTotalPurchaseAmount/{userId}")
    public List<User> getTop3UsersByTotalPurchaseAmount(@PathVariable int userId) {
        return sellerService.getTop3UsersByTotalPurchaseAmount(userId);
    }
}
