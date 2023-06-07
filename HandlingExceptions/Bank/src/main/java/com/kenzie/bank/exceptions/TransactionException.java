package com.kenzie.bank.exceptions;

public class TransactionException extends Exception {

    private static final long serialVersionUID = -3764844266941547495L;

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable e) {
        super(message, e);
    }
}