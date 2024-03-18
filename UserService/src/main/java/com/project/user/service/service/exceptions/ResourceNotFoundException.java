package com.project.user.service.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource Not Found On server!!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
