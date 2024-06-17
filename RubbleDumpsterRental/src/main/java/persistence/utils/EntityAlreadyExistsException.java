package com.persistence.utils;

public class EntityAlreadyExistsException extends  RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
