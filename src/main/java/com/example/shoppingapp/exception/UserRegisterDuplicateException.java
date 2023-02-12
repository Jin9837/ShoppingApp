package com.example.shoppingapp.exception;

public class UserRegisterDuplicateException extends RuntimeException{


    public UserRegisterDuplicateException(String message){
        super(String.format(message));

    }
}
