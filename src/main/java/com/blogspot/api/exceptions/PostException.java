package com.blogspot.api.exceptions;

public class PostException extends RuntimeException{
    private static final long serailVerisionUID = 1;

    public PostException(String message){
        super(message);
    }
    
}
