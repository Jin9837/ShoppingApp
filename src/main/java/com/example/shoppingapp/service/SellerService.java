package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.OrderDao;
import com.example.shoppingapp.dao.OrderProductDao;
import com.example.shoppingapp.dao.ProductDao;
import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.domain.request.SellerAddProductRequest;
import com.example.shoppingapp.domain.request.SellerModifyProductRequest;
import com.example.shoppingapp.domain.request.UpdateProcessingOrderBySeller;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import javassist.NotFoundException;
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

    @Autowired
    private OrderProductDao orderProductDao;

    @Transactional
    public List<Orders> getAllOrdersBySeller(int userId) {
        return orderDao.getAllOrdersBySeller(userId);
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

    @Transactional
    public void updateProcessingOrderToCompleteBySellerAndOrderId(int userId, UpdateProcessingOrderBySeller updateProcessingOrderBySeller) throws NotFoundException, InvalidCredentialsException {
        orderDao.updateProcessingOrderToCompleteBySellerAndOrderId(userId, updateProcessingOrderBySeller);
    }


    @Transactional
    public void cancelOrderBySellerWithOrderIdAndUserId(int userId, UpdateProcessingOrderBySeller updateProcessingOrderBySeller) throws NotFoundException, InvalidCredentialsException {
        orderDao.cancelOrderBySellerWithOrderIdAndUserId(userId, updateProcessingOrderBySeller);
    }

    @Transactional
    public Orders getOrdersBySellerAndOrderId(int userId, int orderId) {
        return orderDao.getOrdersBySellerAndOrderId(userId, orderId);
    }

    @Transactional
    public List<Product> getMostProfitableProductsBySeller(int userId) throws InvalidCredentialsException {
        return orderProductDao.getMostProfitableProductsBySeller(userId);
    }

    @Transactional
    public List<Product> getTop3FrequentlyPurchasedProducts(int userId) throws InvalidCredentialsException {
        return orderProductDao.getTop3FrequentlyPurchasedProducts(userId);
    }


    @Transactional
    public int getTotalSoldItemsBySeller(int userId) {
        return orderProductDao.getTotalSoldItemsBySeller(userId);
    }


    @Transactional
    public List<User> getTop3UsersByTotalPurchaseAmount(int userId) {
        return orderProductDao.getTop3UsersByTotalPurchaseAmount(userId);
    }

}