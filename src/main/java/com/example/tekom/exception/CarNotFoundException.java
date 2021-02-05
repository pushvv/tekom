package com.example.tekom.exception;

public class CarNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Car not found. Car id: ";

    public CarNotFoundException(long id) {
        super(MESSAGE + id);
    }
}
