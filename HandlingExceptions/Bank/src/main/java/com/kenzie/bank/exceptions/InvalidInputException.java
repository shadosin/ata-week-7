package com.kenzie.bank.exceptions;

public class InvalidInputException extends RuntimeException {

    private static final long serialVersionUID = 5786867834083962866L;

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable e) {
        super(message, e);
    }
}