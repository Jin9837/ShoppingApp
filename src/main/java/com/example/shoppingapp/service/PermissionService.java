package com.example.shoppingapp.service;

import com.example.shoppingapp.dao.PermissionsDao;
import com.example.shoppingapp.domain.entity.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
public class PermissionService {

    private PermissionsDao permissionsDao;

    @Autowired
    public PermissionService(PermissionsDao permissionsDao) {
        this.permissionsDao = permissionsDao;
    }

    @Transactional
    public Permissions findByRole(String role){
        return permissionsDao.findByRole(role);
    }
}
