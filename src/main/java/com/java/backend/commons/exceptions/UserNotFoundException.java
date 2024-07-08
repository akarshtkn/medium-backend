package com.java.backend.commons.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String emailOrId){
        super("User not found with email or id : " + emailOrId);
    }
}
