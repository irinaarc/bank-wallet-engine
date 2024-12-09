package com.example.demo.util;

public class InvalidOperationTypeException extends IllegalArgumentException {
    public InvalidOperationTypeException(String message) {
        super(message);
    }
}
