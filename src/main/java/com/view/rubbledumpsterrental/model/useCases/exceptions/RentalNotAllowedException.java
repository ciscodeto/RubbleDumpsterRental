package com.view.rubbledumpsterrental.model.useCases.exceptions;

public class RentalNotAllowedException extends RuntimeException{
    public RentalNotAllowedException(String message) {
        super(message);
    }
}
