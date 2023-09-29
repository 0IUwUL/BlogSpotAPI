package com.blogspot.api.exceptions;

public class UserBadRequest extends RuntimeException{
    private static final long serailVerisionUID = 5;

    public UserBadRequest(String message){
        super(message);
    }
    
}
