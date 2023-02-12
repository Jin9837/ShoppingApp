package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.ProductDao;
import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@EnableTransactionManagement
public class ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public List<Product> viewAllValidProducts() {
        return productDao.getAllProducts();
    }

    @Transactional
    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }

    @Transactional
    public List<Orders> getOrdersByUserId(int userId) {
        return productDao.getOrdersByUserId(userId);
    }

    @Transactional
    public void purchaseProduct(int productId, int userId, int quantity) throws NotEnoughInventoryException {
        productDao.purchaseProduct(productId, userId, quantity);
    }
}
