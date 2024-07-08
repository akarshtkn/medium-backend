package com.java.backend.commons.exceptions;

public class BlogNotFoundException extends RuntimeException{

    public BlogNotFoundException(String id){
        super("Blog not found with id : " + id);
    }
}
