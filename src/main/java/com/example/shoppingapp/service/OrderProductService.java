package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.OrderProductDao;
import com.example.shoppingapp.domain.entity.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
public class OrderProductService {
    private OrderProductDao orderProductDao;

    @Autowired
    public OrderProductService(OrderProductDao orderProductDao) {
        this.orderProductDao = orderProductDao;
    }

    @Transactional
    public OrderProduct getOrderProductByOrderId(int userId, int orderId) {
        return orderProductDao.getOrderProductByOrderId(userId, orderId);
    }
}
