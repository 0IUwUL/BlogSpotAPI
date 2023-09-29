package com.blogspot.api.exceptions;

public class CommentBadRequest extends RuntimeException{
    private static final long serailVerisionUID = 6;

    public CommentBadRequest(String message){
        super(message);
    }
    
}
