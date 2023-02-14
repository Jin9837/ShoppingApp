package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.UserPermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
public class UserPermissionService {

    private UserPermissionDao userPermissionDao;


    @Autowired
    public UserPermissionService(UserPermissionDao userPermissionDao) {
        this.userPermissionDao = userPermissionDao;
    }

//    @Transactional

}
