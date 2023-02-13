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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            Predicate predicate = cb.equal(root.get("orderId"), orderId);
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
            String hql = "FROM OrderProduct op JOIN Orders o ON op.orderId = o.orderId WHERE o.userId = :userId AND op.orderId = :orderId";
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


}
