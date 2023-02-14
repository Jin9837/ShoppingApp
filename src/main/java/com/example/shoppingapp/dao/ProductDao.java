package com.example.shoppingapp.dao;

import com.example.shoppingapp.domain.entity.Orders;
import com.example.shoppingapp.domain.entity.Product;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.domain.request.SellerAddProductRequest;
import com.example.shoppingapp.domain.request.SellerModifyProductRequest;
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
import java.util.Objects;

@Repository
public class ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Product> getAllProducts() {
        Session session;
        List<Product> products = null;
        try {
            session = sessionFactory.getCurrentSession();

            // Get all products where the quantity is greater than 0
            String hql = "SELECT p.name FROM Product p WHERE stockQuantity > 0";
            Query query = session.createQuery(hql);
            products = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }



    public List<Product> getAllProductsBySeller(int userId) {
        Session session;
        List<Product> products = null;
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
                String hql = "FROM Product p";
                Query query = session.createQuery(hql);
                products = query.list();
            }
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



    public void modifyProductBySellerAndProductId(int userId, SellerModifyProductRequest sellerModifyProductRequest) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Check if the user is a seller
            User user = session.get(User.class, userId);
            String permissionRole = user.getPermissionRole();
            if (!Objects.equals(permissionRole, "seller")) {
                throw new InvalidCredentialsException("You are not a seller, cannot modify product.");
            }

            // Retrieve the product by productId
            int productId = sellerModifyProductRequest.getProductId();
            Product product = session.get(Product.class, productId);

            // Update the product with the new values
            product.setName(sellerModifyProductRequest.getName());
            product.setDescription(sellerModifyProductRequest.getDescription());
            product.setRetailPrice(sellerModifyProductRequest.getRetailPrice());
            product.setWholesalePrice(sellerModifyProductRequest.getWholesalePrice());
            product.setStockQuantity(sellerModifyProductRequest.getStockQuantity());

            // Save the changes to the database
            session.update(product);

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addProductBySellerAndProductId(int userId, SellerAddProductRequest sellerAddProductRequest) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Check if the user is a seller
            User user = session.get(User.class, userId);
            String permissionRole = user.getPermissionRole();
            if (!Objects.equals(permissionRole, "seller")) {
                throw new InvalidCredentialsException("You are not a seller, cannot modify product.");
            }

            Product product = new Product();

            // Update the product with the new values
            product.setName(sellerAddProductRequest.getName());
            product.setDescription(sellerAddProductRequest.getDescription());
            product.setRetailPrice(sellerAddProductRequest.getRetailPrice());
            product.setWholesalePrice(sellerAddProductRequest.getWholesalePrice());
            product.setStockQuantity(sellerAddProductRequest.getStockQuantity());

            // Save the changes to the database
            session.save(product);

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
