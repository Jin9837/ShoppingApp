package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.OrderDao;
import com.example.shoppingapp.dao.ProductDao;
import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.request.SellerAddProductRequest;
import com.example.shoppingapp.domain.request.SellerModifyProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@EnableTransactionManagement
public class SellerService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Transactional
    public List<Orders> getAllOrdersBySeller(int userId) {
        return  orderDao.getAllOrdersBySeller(userId);
    }

    @Transactional
    public List<Product> getAllProductsBySeller(int userId) {
        return productDao.getAllProductsBySeller(userId);
    }

    @Transactional
    public void modifyProductsBySellerAndProductId(int userId, SellerModifyProductRequest sellerModifyProductRequest) {
        productDao.modifyProductBySellerAndProductId(userId, sellerModifyProductRequest);
    }

    @Transactional
    public void addProductBySellerAndProductId(int userId, SellerAddProductRequest sellerAddProductRequest) {
        productDao.addProductBySellerAndProductId(userId, sellerAddProductRequest);
    }
}
