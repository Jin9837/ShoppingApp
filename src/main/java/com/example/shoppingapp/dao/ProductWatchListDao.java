package com.example.shoppingapp.dao;

import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.entity.ProductWatchList;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class ProductWatchListDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addToProductWatchList(int userId, int productId){
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, userId);
            Product product = session.get(Product.class, productId);
            if (user != null && product != null && product.getStockQuantity() > 0) {
                ProductWatchList productWatchLists = new ProductWatchList();
                productWatchLists.setUserId(userId);
                productWatchLists.setProductId(productId);
                session.saveOrUpdate(productWatchLists);
            } else {
                throw new NotEnoughInventoryException("Product/User does not exist or StockQuantity is not enough");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeFromProductWatchList(int userId, int productId) throws EntityNotFoundException{
//        Session session = null;
//        try {
//            session = sessionFactory.getCurrentSession();
//            Query query = session.createQuery("DELETE FROM ProductWatchList WHERE userId = :userId AND productId = :productId");
//            query.setParameter("userId", userId);
//            query.setParameter("productId", productId);
//            int result = query.executeUpdate();
//            if (result == 0) {
//                throw new EntityNotFoundException("ProductWatchList with userId " + userId + " and productId " + productId + " was not found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM ProductWatchList WHERE userId = :userId AND productId = :productId");
            query.setParameter("userId", userId);
            query.setParameter("productId", productId);
            int result = query.executeUpdate();
            if (result == 0) {
                throw new EntityNotFoundException("ProductWatchList with userId " + userId + " and productId " + productId + " was not found");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<ProductWatchList> getAllProductWatchList(int userId){
        Session session;
        List<ProductWatchList> productWatchLists = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            // Get all products where the quantity is greater than 0
//            String hql = "FROM ProductWatchList";
//            Query query = session.createQuery(hql);
//            productWatchLists = query.list();

            String hql = "FROM ProductWatchList pw LEFT JOIN Product p ON pw.productId = p.productId WHERE pw.userId = :userId AND p.stockQuantity > 0";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            productWatchLists = query.list();


            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productWatchLists;
    }


}
