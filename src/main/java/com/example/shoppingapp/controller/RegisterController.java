package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.Permissions;
import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.domain.entity.UserPermission;
import com.example.shoppingapp.domain.request.RegisterRequest;
import com.example.shoppingapp.service.PermissionService;
import com.example.shoppingapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegisterController {

    private final UserService userService;

    private final PermissionService permissionService;

    @Autowired
    public RegisterController(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

//    Test JSON Format:
//     POST http://localhost:8080/register
//    {
//            "email": "jin@gmail.com",
//            "username" : "jin",
//            "password" : "jin",
//            "permissionRole" : "seller"
//    }
    @PostMapping("/register")
    public String addUser(@RequestBody RegisterRequest registerRequest){
//        System.out.println("isSeller: " + user.isSeller());
//         userService.addUser(user);
//         return "Register success";


        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());

        Permissions permission = permissionService.findByRole(registerRequest.getPermissionRole());
        UserPermission userPermission = new UserPermission();
        userPermission.setUser(user);
        userPermission.setPermission(permission);

        user.getUserPermissions().add(userPermission);
        userService.addUser(user);

        return "Register success";
    }


}
