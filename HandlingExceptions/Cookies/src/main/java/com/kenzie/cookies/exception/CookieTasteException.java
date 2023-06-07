package com.kenzie.cookies.exception;

public class CookieTasteException extends Exception {
    public CookieTasteException() {}

    public CookieTasteException(String message) {
        super(message);
    }

    public CookieTasteException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieTasteException(Throwable cause) {
        super(cause);
    }
}