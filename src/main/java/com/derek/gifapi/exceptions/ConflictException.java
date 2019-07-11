package com.derek.gifapi.exceptions;

/**
 * This exception is thrown if a create action failed because there was a preexisting entry in the database. This should translate to a 409 HTTP response code
 */
public class ConflictException extends Exception {
    public ConflictException() {
        super();
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }

    protected ConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
