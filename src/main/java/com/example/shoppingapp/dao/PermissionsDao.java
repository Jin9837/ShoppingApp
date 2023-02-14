package com.example.shoppingapp.dao;

import com.example.shoppingapp.domain.entity.Permissions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionsDao {

    @Autowired
    private SessionFactory sessionFactory;


    public Permissions findByRole(String role) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Permissions WHERE permissionRole = :role", Permissions.class)
                .setParameter("role", role)
                .uniqueResult();
    }
}
