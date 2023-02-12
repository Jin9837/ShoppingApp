package com.example.shoppingapp.dao;

import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;


    public void addUser(User user){
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            // Check if the username or email already exists in the User table
            String hql = "FROM User WHERE username = :username OR email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("username", user.getUsername());
            query.setParameter("email", user.getEmail());
            List<User> result = query.list();

            // If the result is not empty, the username or email already exists
            if (result.isEmpty()) {
                session.saveOrUpdate(user);
            } else {
                System.out.println("Error: username or email already exists in the database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public User login(String username, String password) throws InvalidCredentialsException {
        Session session;
        User user = null;
        try {
            session = sessionFactory.getCurrentSession();

            // Check if the username and password match a record in the User table
            String hql = "FROM User WHERE username = :username AND password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<User> result = query.list();

            // If the result is not empty, the username and password match a record in the User table
            if (!result.isEmpty()) {
                user = result.get(0);
            } else {
                throw new InvalidCredentialsException("Incorrect credentials for Username and Password, please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
