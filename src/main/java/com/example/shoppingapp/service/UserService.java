package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.UserDao;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
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

    @Transactional
    public User login(String username, String password) throws InvalidCredentialsException {
        return userDao.login(username, password);
    }
}
