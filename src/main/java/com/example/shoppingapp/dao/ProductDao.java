package com.example.shoppingapp.dao;

import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import javassist.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Product> getAllProducts() {
        Session session;
        List<Product> products = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            // Get all products where the quantity is greater than 0
            String hql = "SELECT p.name FROM Product p WHERE stockQuantity > 0";
            Query query = session.createQuery(hql);
            products = query.list();

            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }


    public Product getProductById(int productId) {
        Session session;
        Product product = null;
        try {
            session = sessionFactory.getCurrentSession();
            String hql = "SELECT new com.example.shoppingapp.domain.entity.Product(p.id, p.name, p.description, p.retailPrice) FROM Product p WHERE stockQuantity > 0 AND productId = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
            List<Product> result = query.list();
            if (!result.isEmpty()) {
                product = result.get(0);
            } else {
                System.out.println("Do not have this product");
            }

//            product = session.get(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }




}
