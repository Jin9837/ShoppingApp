package com.example.shoppingapp.dao;

import com.example.shoppingapp.domain.entity.OrderProduct;
import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.domain.request.UpdateProcessingOrderBySeller;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import javassist.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private OrderProductDao orderProductDao;
    @Autowired
    private ProductDao productDao;


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
                order.setUser(session.get(User.class, userId));

                order.setOrderStatus("processing");
                LocalDateTime dateTime = LocalDateTime.now();
                Timestamp datePlaced = Timestamp.valueOf(dateTime);
                order.setDatePlaced(datePlaced);
                session.saveOrUpdate(order);

                // Load the Orders and Product objects before setting them on the OrderProduct object
                order = session.get(Orders.class, order.getOrderId());
                product = session.get(Product.class, product.getProductId());

                // Create a new orderProduct for the user and his order
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(order);
                orderProduct.setProduct(product);
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
            String hql = "FROM Orders o WHERE o.user.userId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            orders = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Orders getOrdersBySellerAndOrderId(int userId, int orderId) {
        Session session;
        List<Orders> orders = null;
        try {
            session = sessionFactory.getCurrentSession();

            User user = session.get(User.class, userId);
            String permissionRole = user.getPermissionRole();
            if (!Objects.equals(permissionRole, "seller"))
            {
                throw new InvalidCredentialsException("You are not a seller, cannot view this specific Order information page.");
            }

            // Get all orders for the user with the given id
            String hql = "FROM Orders o WHERE o.orderId = :orderId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            orders = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders.get(0);
    }


    // verity that the useId is belongs to a seller permission, otherwise throw not a seller exception
    public List<Orders> getAllOrdersBySeller(int userId) {
        Session session;
        List<Orders> orders = null;
        try {
            session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, userId);
            String permissionRole = user.getPermissionRole();
            if (!Objects.equals(permissionRole, "seller"))
            {
                throw new InvalidCredentialsException("You are not a seller, cannot view this Order infomatoin page.");
            }
            else
            {
                // Get all orders for the user with the given id
                String hql = "FROM Orders o";
                Query query = session.createQuery(hql);
                orders = query.list();
            }
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
        int productId = orderProduct.getProduct().getProductId();
        Product product = session.get(Product.class, productId);
        if (product == null) {
            throw new NotFoundException("Product with id " + productId + " not found");
        }
        int newQuantity = product.getStockQuantity() + quantity;
        product.setStockQuantity(newQuantity);
        session.saveOrUpdate(product);
        session.delete(orderProduct);
    }


    public void cancelOrderBySellerWithOrderIdAndUserId(int userId, UpdateProcessingOrderBySeller updateProcessingOrderBySeller) throws NotFoundException, InvalidCredentialsException {
        Session session = sessionFactory.getCurrentSession();

        User user = session.get(User.class, userId);
        String permissionRole = user.getPermissionRole();
        if (!Objects.equals(permissionRole, "seller")) {
            throw new InvalidCredentialsException("You are not a seller, cannot cancel the order.");
        }

        Orders order = session.get(Orders.class, updateProcessingOrderBySeller.getOrderId());
        OrderProduct orderProduct = orderProductDao.getOrderProductByOrderId(updateProcessingOrderBySeller.getOrderId());

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (!order.getOrderStatus().equals("processing")) {
            throw new IllegalStateException("Cannot cancel a non-processing order");
        }

        if (updateProcessingOrderBySeller.getNewStatus().equals("completed")) {
            throw new IllegalStateException("Cannot change order status to completed");
        }

        order.setOrderStatus(updateProcessingOrderBySeller.getNewStatus());
        session.update(order);

        int quantity = orderProduct.getPurchasedQuantity();
        int productId = orderProduct.getProduct().getProductId();
        Product product = session.get(Product.class, productId);
        if (product == null) {
            throw new NotFoundException("Product with id " + productId + " not found");
        }
        int newQuantity = product.getStockQuantity() + quantity;
        product.setStockQuantity(newQuantity);
        session.saveOrUpdate(product);
        session.delete(orderProduct);
    }


    public void updateProcessingOrderToCompleteBySellerAndOrderId(int userId, UpdateProcessingOrderBySeller updateProcessingOrderBySeller) throws NotFoundException, InvalidCredentialsException {
        Session session = sessionFactory.getCurrentSession();

        User user = session.get(User.class, userId);
        String permissionRole = user.getPermissionRole();
        if (!Objects.equals(permissionRole, "seller")) {
            throw new InvalidCredentialsException("You are not a seller, cannot complete the order.");
        }

        Orders order = session.get(Orders.class, updateProcessingOrderBySeller.getOrderId());
        OrderProduct orderProduct = orderProductDao.getOrderProductByOrderId(updateProcessingOrderBySeller.getOrderId());

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.getOrderStatus().equals("completed")) {
            throw new IllegalStateException("The order is already completed, cannot complete again");
        }

        if (!order.getOrderStatus().equals("processing")) {
            throw new IllegalStateException("Cannot complete a non-processing order");
        }


        order.setOrderStatus(updateProcessingOrderBySeller.getNewStatus());
        session.update(order);
    }




    public List<Product> getTop3FrequentlyPurchasedItems() {
        Session session;
        List<Product> products = null;
        try {
            session = sessionFactory.getCurrentSession();

            // Get the top 3 frequently purchased products based on the product ID and order status
            String hql = "SELECT op.product.productId, SUM(op.purchasedQuantity) AS totalQuantity " +
                    "FROM OrderProduct op " +
                    "JOIN Orders o ON op.order.orderId = o.orderId " +
                    "WHERE o.orderStatus IN ('complete', 'processing') " +
                    "GROUP BY op.product.productId " +
                    "ORDER BY totalQuantity DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(3);
            List<Object[]> results = query.list();

            // Get the product objects for the top 3 products based on their product IDs
            products = new ArrayList<>();
            for (Object[] result : results) {
                int productId = (int) result[0];
                Product product = session.get(Product.class, productId);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }



    public List<Product> getTop3FrequentlyPurchasedItems(int userId) {
        Session session;
        List<Product> top3Products = null;
        try {
            session = sessionFactory.getCurrentSession();

            // Get the top 3 products with the most frequent product IDs
            String hql = "SELECT op.product.productId, SUM(op.purchasedQuantity) AS totalPurchased " +
                    "FROM OrderProduct op " +
                    "JOIN Orders o ON op.order.orderId = o.orderId " +
                    "WHERE o.user.userId = :userId AND (o.orderStatus = 'complete' OR o.orderStatus = 'processing') " +
                    "GROUP BY op.product.productId " +
                    "ORDER BY totalPurchased DESC";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setMaxResults(3);
            List<Object[]> result = query.list();

            // Get the product details for each product ID
            top3Products = new ArrayList<>();
            for (Object[] row : result) {
                int productId = (int) row[0];
                Product product = productDao.getProductById(productId);
                top3Products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return top3Products;
    }


}
