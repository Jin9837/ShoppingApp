package com.example.shoppingapp.controller;

import com.example.shoppingapp.domain.entity.User;
import com.example.shoppingapp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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



    //Test JSON Format
    //{
    //    "email": "jin@gmail.com",
    //        "username" : "jin",
    //        "password" : "jin",
    //        "isSeller" : true
    //}
    @PostMapping("/register")
    public String addUser(@RequestBody User user){
        System.out.println("isSeller: " + user.isSeller());
         userService.addUser(user);
         return "Register success";
    }
//    @PostMapping("/register")
//    public String addUser(@RequestBody String json) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        User user = mapper.readValue(json, User.class);
//        System.out.println("isSeller: " + user.isSeller());
//        userService.addUser(user);
//        return "Register success";
//    }

}
