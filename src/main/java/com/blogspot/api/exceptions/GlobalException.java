package com.blogspot.api.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice

public class GlobalException {
    @ExceptionHandler(PostException.class)
    public ResponseEntity<ErrorObject> handlePostNotFoundException(PostException ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus_code(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ErrorObject> handleCommentNotFoundException(CommentException ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus_code(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(UserException ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus_code(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentBadRequest.class)
    public ResponseEntity<ErrorObject> handleCommentBadRequestException(CommentBadRequest ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus_code(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostBadRequest.class)
    public ResponseEntity<ErrorObject> handlePostBadRequestException(PostBadRequest ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus_code(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserBadRequest.class)
    public ResponseEntity<ErrorObject> handleUserBadRequestException(UserBadRequest ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus_code(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }
}
