package com.example.shoppingapp.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserPermissionDao {

    @Autowired
    private SessionFactory sessionFactory;


}
