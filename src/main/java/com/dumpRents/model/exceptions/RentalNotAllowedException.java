package com.dumpRents.model.exceptions;

public class RentalNotAllowedException extends RuntimeException{
    public RentalNotAllowedException(String message) {
        super(message);
    }
}
