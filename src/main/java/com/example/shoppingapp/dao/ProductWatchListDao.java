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

    public void addToProductWatchList(int userId, int productId) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, userId);
            Product product = session.get(Product.class, productId);
            if (user != null && product != null && product.getStockQuantity() > 0) {
                ProductWatchList productWatchList = new ProductWatchList();
                productWatchList.setUser(user);
                productWatchList.setProduct(product);
                session.saveOrUpdate(productWatchList);
            } else {
                throw new NotEnoughInventoryException("Product/User does not exist or StockQuantity is not enough");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void removeFromProductWatchList(int userId, int productId) throws EntityNotFoundException{

        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("DELETE FROM ProductWatchList WHERE user.userId = :userId AND product.productId = :productId");
            query.setParameter("userId", userId);
            query.setParameter("productId", productId);
            int result = query.executeUpdate();
            if (result == 0) {
                throw new EntityNotFoundException("ProductWatchList with userId " + userId + " and productId " + productId + " was not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<ProductWatchList> getAllProductWatchList(int userId) {
        List<ProductWatchList> productWatchLists = null;
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            String hql = "FROM ProductWatchList pw LEFT JOIN FETCH pw.product WHERE pw.user.userId = :userId AND pw.product.stockQuantity > 0";
            Query<ProductWatchList> query = session.createQuery(hql, ProductWatchList.class);
            query.setParameter("userId", userId);
            productWatchLists = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productWatchLists;
    }


}
