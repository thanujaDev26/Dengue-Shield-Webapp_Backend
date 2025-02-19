package com.dengue_webapp.dengue_webapp.exceptions;

public class DataAlreadyExistsException extends RuntimeException{
    public  DataAlreadyExistsException(String message){
        super(message);
    }
}
