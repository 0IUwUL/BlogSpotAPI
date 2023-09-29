package com.blogspot.api.exceptions;

public class PostBadRequest extends RuntimeException{
    private static final long serailVerisionUID = 4;

    public PostBadRequest(String message){
        super(message);
    }
    
}
