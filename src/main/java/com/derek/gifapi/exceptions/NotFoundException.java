package com.derek.gifapi.exceptions;

/**
 * This exception is thrown whenever a get action is performed and a piece of the requested data was missing. This should translate to a 404 HTTP response code.
 */
public class NotFoundException extends Exception {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    protected NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}