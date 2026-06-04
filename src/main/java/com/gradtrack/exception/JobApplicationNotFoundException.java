package com.gradtrack.exception;

public class JobApplicationNotFoundException extends RuntimeException{
    public JobApplicationNotFoundException (Long id){
        super ("Job application not found with id: "+ id);
    }
}
