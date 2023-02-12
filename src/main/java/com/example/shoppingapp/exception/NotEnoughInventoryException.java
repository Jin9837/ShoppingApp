package com.example.shoppingapp.exception;

public class NotEnoughInventoryException extends Exception {

    public NotEnoughInventoryException(String message) {
        super(String.format(message));
    }
}
