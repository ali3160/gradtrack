package com.gradtrack.exception;

public class ApplicationTaskNotFoundException extends RuntimeException{

    public ApplicationTaskNotFoundException (Long id){
        super("Application task not found with id: " + id);
    }

}
