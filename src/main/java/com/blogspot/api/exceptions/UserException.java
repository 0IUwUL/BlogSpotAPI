package com.blogspot.api.exceptions;

public class UserException extends RuntimeException{
    private static final long serailVerisionUID = 3;

    public UserException(String message){
        super(message);
    }
}
