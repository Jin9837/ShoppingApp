package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String addUser(@RequestBody User user){
         userService.addUser(user);
         return "Register success";
    }
}
