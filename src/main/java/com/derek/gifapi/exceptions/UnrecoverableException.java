package com.derek.gifapi.exceptions;

/**
 * This exception is thrown whenever the API gets into a state it can't recover from. This should translate to a 500 HTTP response code.
 */
public class UnrecoverableException extends Exception {
    public UnrecoverableException() {
        super();
    }

    public UnrecoverableException(String message) {
        super(message);
    }

    public UnrecoverableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnrecoverableException(Throwable cause) {
        super(cause);
    }

    protected UnrecoverableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
