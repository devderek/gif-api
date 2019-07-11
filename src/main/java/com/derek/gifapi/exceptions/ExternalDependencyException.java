package com.derek.gifapi.exceptions;

/**
 * This exception is thrown when an external system fails in a way that this application can not recover from. It should translate to a 503 HTTP response code.
 */
public class ExternalDependencyException extends Exception {
    public ExternalDependencyException() {
        super();
    }

    public ExternalDependencyException(String message) {
        super(message);
    }

    public ExternalDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalDependencyException(Throwable cause) {
        super(cause);
    }

    protected ExternalDependencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
