package com.ruslan.backendtrello.service.exception;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException(String message){
        super(message);
    }
}
