package com.example.shoppingapp.AOP;

import com.example.shoppingapp.domain.response.LoginResponse;
import com.example.shoppingapp.exception.InvalidCredentialsException;
import com.example.shoppingapp.exception.NotEnoughInventoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {InvalidCredentialsException.class, BadCredentialsException.class})
    public ResponseEntity<LoginResponse> handleException(Exception e){
        return new ResponseEntity(LoginResponse.builder().message(e.getMessage()).build(), HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(value = {InvalidCredentialsException.class})
//    public ResponseEntity<LoginResponse> handleDemoNotFoundException(InvalidCredentialsException e){
//        return new ResponseEntity(LoginResponse.builder().message(e.getMessage()).build(), HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler(value = {NotEnoughInventoryException.class})
    public ResponseEntity handleNotEnoughInventoryException(NotEnoughInventoryException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }



}
