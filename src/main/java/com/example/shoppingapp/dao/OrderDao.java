package com.example.shoppingapp.dao;

import com.example.shoppingapp.domain.entity.OrderProduct;
import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import javassist.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private OrderProductDao orderProductDao;

    public void purchaseProduct(int productId, int userId, int quantity) throws NotEnoughInventoryException {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            // Decrement the quantity of the product with the given id
            Product product = session.get(Product.class, productId);
            if (product == null) {
                throw new NotFoundException("Product with id " + productId + " not found");
            }
            int newQuantity = product.getStockQuantity() - quantity;
            if (newQuantity < 0) {
                throw new NotEnoughInventoryException("Do not have enough stock_quantity");
            } else {
                product.setStockQuantity(newQuantity);
                session.saveOrUpdate(product);

                // Create a new order for the user with the given id
                Orders order = new Orders();
                order.setUserId(userId);
                order.setOrderStatus("processing");
                LocalDateTime dateTime = LocalDateTime.now();
                Timestamp datePlaced = Timestamp.valueOf(dateTime);
                order.setDatePlaced(datePlaced);
                session.saveOrUpdate(order);


                // Create a new orderProduct for the user and his order
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrderId(order.getOrderId());
                orderProduct.setProductId(product.getProductId());
                orderProduct.setPurchasedQuantity(quantity);
                orderProduct.setExecutionRetailPrice(product.getRetailPrice());
                orderProduct.setExecutionWholesalePrice(product.getWholesalePrice());
                session.saveOrUpdate(orderProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }


    public List<Orders> getOrdersByUserId(int userId) {
        Session session;
        List<Orders> orders = null;
        try {
            session = sessionFactory.getCurrentSession();

            // Get all orders for the user with the given id
            String hql = "FROM Orders WHERE userId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            orders = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }


    public void cancelOrderByOrderId(int orderId, String newStatus) throws NotFoundException {
        Session session = sessionFactory.getCurrentSession();
        Orders order = session.get(Orders.class, orderId);
        OrderProduct orderProduct = orderProductDao.getOrderProductByOrderId(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (!order.getOrderStatus().equals("processing")) {
            throw new IllegalStateException("Cannot cancel a non-processing order");
        }

        if (newStatus.equals("completed")) {
            throw new IllegalStateException("Cannot change order status to completed");
        }

        order.setOrderStatus(newStatus);
        session.update(order);

        int quantity = orderProduct.getPurchasedQuantity();
        int productId = orderProduct.getProductId();
        Product product = session.get(Product.class, productId);
        if (product == null) {
            throw new NotFoundException("Product with id " + productId + " not found");
        }
        int newQuantity = product.getStockQuantity() + quantity;
        product.setStockQuantity(newQuantity);
        session.saveOrUpdate(product);
        session.delete(orderProduct);
    }


}
