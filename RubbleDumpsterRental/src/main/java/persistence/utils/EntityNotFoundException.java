package com.persistence.utils;

public class EntityNotFoundException extends  RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
