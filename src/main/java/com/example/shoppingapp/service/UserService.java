package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.UserDao;
import com.example.shoppingapp.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void addUser(User user){
        userDao.addUser(user);
    }
}
