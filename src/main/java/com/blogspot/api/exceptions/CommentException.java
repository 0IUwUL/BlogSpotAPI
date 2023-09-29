package com.blogspot.api.exceptions;

public class CommentException extends RuntimeException{
    private static final long serailVerisionUID = 2;

    public CommentException(String message){
        super(message);
    }
}
