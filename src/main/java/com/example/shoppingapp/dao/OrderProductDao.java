package com.example.shoppingapp.dao;

import com.example.shoppingapp.domain.entity.OrderProduct;
import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import javassist.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OrderProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    public OrderProduct getOrderProductByOrderId(int orderId) {
        Session session = null;
        Optional<OrderProduct> orderProduct = null;
        try {
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<OrderProduct> cq = cb.createQuery(OrderProduct.class);
            Root<OrderProduct> root = cq.from(OrderProduct.class);
            Join<OrderProduct, Orders> ordersJoin = root.join("order");
            Predicate predicate = cb.equal(ordersJoin.get("orderId"), orderId);
            cq.select(root).where(predicate);
            orderProduct = session.createQuery(cq).uniqueResultOptional();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (orderProduct.isPresent())? orderProduct.get() : null;
    }




    public OrderProduct getOrderProductByOrderId(int userId, int orderId) {
        Session session;
        OrderProduct orderProduct = null;
        try {
            session = sessionFactory.getCurrentSession();

            // Get the OrderProduct by its order ID and user ID
            String hql = "FROM OrderProduct op JOIN Orders o ON op.order.orderId = o.orderId WHERE o.user.userId = :userId AND op.order.orderId = :orderId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("orderId", orderId);
            Object[] result = (Object[]) query.uniqueResult();
            if (result != null) {
                orderProduct = (OrderProduct) result[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderProduct;
    }


    public List<Product> getMostProfitableProductsBySeller(int userId) throws InvalidCredentialsException {

        String hql = "SELECT op.product.productId, SUM(op.executionRetailPrice - op.executionWholesalePrice) " +
                "FROM OrderProduct op " +
                "WHERE op.executionRetailPrice > 0 AND op.executionWholesalePrice > 0 " +
                "AND op.order.orderStatus IN ('processing', 'completed') " +
                "GROUP BY op.product.productId " +
                "ORDER BY SUM(op.executionRetailPrice - op.executionWholesalePrice) DESC";

        Session session = sessionFactory.getCurrentSession();

        User user = session.get(User.class, userId);
        String permissionRole = user.getPermissionRole();
        if (!Objects.equals(permissionRole, "seller"))
        {
            throw new InvalidCredentialsException("You are not a seller, cannot view this specific Order information page.");
        }

        List<Object[]> results = session.createQuery(hql, Object[].class).setMaxResults(1).getResultList();
        if (!results.isEmpty()) {
            int productId = (int) results.get(0)[0];
            return sessionFactory.getCurrentSession().createQuery("FROM Product WHERE productId = :productId", Product.class)
                    .setParameter("productId", productId)
                    .getResultList();
        } else {
            return Collections.emptyList();
        }
    }


    public List<Product> getTop3FrequentlyPurchasedProducts(int userId) throws InvalidCredentialsException {
        Session session = null;

        List<Product> products = null;
        try {
            session = sessionFactory.getCurrentSession();

            User user = session.get(User.class, userId);
            String permissionRole = user.getPermissionRole();
            if (!Objects.equals(permissionRole, "seller"))
            {
                throw new InvalidCredentialsException("You are not a seller, cannot view this specific Order information page.");
            }

            // Get the top 3 frequently purchased products based on the number of times each product has been purchased
            String hql = "SELECT op.product.productId, SUM(op.purchasedQuantity) as totalQuantity " +
                    "FROM OrderProduct op JOIN Orders o ON op.order.orderId = o.orderId " +
                    "WHERE o.orderStatus NOT IN ('canceled', 'processing') " +
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


    public int getTotalSoldItemsBySeller(int userId) {
        Session session = null;
        int totalSoldItems = 0;
        try {
            session = sessionFactory.getCurrentSession();

            User user = session.get(User.class, userId);
            String permissionRole = user.getPermissionRole();
            if (!Objects.equals(permissionRole, "seller"))
            {
                throw new InvalidCredentialsException("You are not a seller, cannot view this specific Order information page.");
            }

            // Get the sum of purchased quantity of all completed orders
            String hql = "SELECT SUM(op.purchasedQuantity) " +
                    "FROM OrderProduct op " +
                    "JOIN op.order o " +
                    "WHERE o.orderStatus = 'completed'";
            Query query = session.createQuery(hql);
            Object result = query.uniqueResult();
            if (result != null) {
                totalSoldItems = ((Number) result).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSoldItems;
    }



    public List<User> getTop3UsersByTotalPurchaseAmount(int userId) {
        Session session;
        List<User> users = null;
        try {
            session = sessionFactory.getCurrentSession();

            User user = session.get(User.class, userId);
            String permissionRole = user.getPermissionRole();
            if (!Objects.equals(permissionRole, "seller"))
            {
                throw new InvalidCredentialsException("You are not a seller, cannot view this specific Order information page.");
            }

            // Get the top 3 users based on the total purchase amount
            String hql = "SELECT op.order.user, SUM(op.executionRetailPrice * op.purchasedQuantity) AS totalAmount " +
                    "FROM OrderProduct op " +
                    "WHERE op.order.orderStatus NOT IN ('canceled', 'processing') " +
                    "GROUP BY op.order.user " +
                    "ORDER BY totalAmount DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(3);
            List<Object[]> results = query.list();

            // Get the user objects for the top 3 users based on their user IDs
            users = new ArrayList<>();
            for (Object[] result : results) {
                User topUser = (User) result[0];
                users.add(topUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }




}
