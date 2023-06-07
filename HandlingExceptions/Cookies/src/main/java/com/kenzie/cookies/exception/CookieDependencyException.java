package com.kenzie.cookies.exception;

public class CookieDependencyException extends RuntimeException {
    public CookieDependencyException() {}

    public CookieDependencyException(String message) {
        super(message);
    }

    public CookieDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieDependencyException(Throwable cause) {
        super(cause);
    }
}