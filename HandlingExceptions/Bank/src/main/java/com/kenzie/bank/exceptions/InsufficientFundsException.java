package com.kenzie.bank.exceptions;

public class InsufficientFundsException extends TransactionException {

    private static final long serialVersionUID = 8450339811165249530L;

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable e) {
        super(message, e);
    }
}