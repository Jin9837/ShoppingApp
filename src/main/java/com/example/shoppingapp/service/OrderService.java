package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.OrderDao;
import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@EnableTransactionManagement
public class OrderService {
    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Transactional
    public List<Orders> getOrdersByUserId(int userId) {
        return orderDao.getOrdersByUserId(userId);
    }

    @Transactional
    public void purchaseProduct(int productId, int userId, int quantity) throws NotEnoughInventoryException {
        orderDao.purchaseProduct(productId, userId, quantity);

    }

    @Transactional
    public void cancelOrderByOrderId(int orderId, String newStatus) throws NotFoundException {
        orderDao.cancelOrderByOrderId(orderId, newStatus);
    }

    @Transactional
    public List<Product> getTop3FrequentlyPurchasedItems() {
        return orderDao.getTop3FrequentlyPurchasedItems();
    }


    @Transactional
    public List<Product> getTop3FrequentlyPurchasedItems(int userId) {
        return orderDao.getTop3FrequentlyPurchasedItems(userId);
    }
}
