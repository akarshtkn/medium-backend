package com.java.backend.commons.exceptions;

public class BlogAccessDeniedException extends IllegalAccessException{

    public BlogAccessDeniedException(String userId){
        super("Access denied for user with id : " + userId);
    }
}
