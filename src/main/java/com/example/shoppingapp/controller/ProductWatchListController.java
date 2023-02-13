package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.ProductWatchList;
import com.example.shoppingapp.domain.request.ProductWatchListRequest;
import com.example.shoppingapp.service.ProductWatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class ProductWatchListController {

    private ProductWatchListService productWatchListService;

    @Autowired
    public ProductWatchListController(ProductWatchListService productWatchListService) {
        this.productWatchListService = productWatchListService;
    }


    //  POST    http://localhost:8080//addToProductWatchList
    //    {
    //        "userId" : 2,
    //            "productId": 1
    //    }
    @PostMapping("/addToProductWatchList")
    public void addToProductWatchList(@RequestBody ProductWatchListRequest productWatchListRequest){
        System.out.println("productWatchListRequest.getUserId(): " + productWatchListRequest.getUserId());
        System.out.println("productWatchListRequest.getProductId(): " + productWatchListRequest.getProductId());
        productWatchListService.addToProductWatchList(productWatchListRequest.getUserId(), productWatchListRequest.getProductId());
    }


    // DELETE  http://localhost:8080/removeFromProductWatchList
    //    {
    //        "userId" : 2,
    //            "productId": 1
    //    }
    @DeleteMapping("/removeFromProductWatchList")
    public void removeFromProductWatchList(@RequestBody ProductWatchListRequest productWatchListRequest) throws EntityNotFoundException {
        productWatchListService.removeFromProductWatchList(productWatchListRequest.getUserId(), productWatchListRequest.getProductId());
    }


    // GET     http://localhost:8080/getAllProductWatchList/2
    @GetMapping("/getAllProductWatchList/{userId}")
    public List<ProductWatchList> getAllProductWatchList(@PathVariable int userId){
        return productWatchListService.getAllProductWatchList(userId);
    }

}
