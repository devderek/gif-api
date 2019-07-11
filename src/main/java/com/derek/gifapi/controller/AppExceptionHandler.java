package com.derek.gifapi.controller;

import com.derek.gifapi.dto.ErrorDto;
import com.derek.gifapi.exceptions.BadRequestException;
import com.derek.gifapi.exceptions.ConflictException;
import com.derek.gifapi.exceptions.NotFoundException;
import com.derek.gifapi.exceptions.UnrecoverableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class handles exceptions that are sent to the controller classes. The exceptions get translated into a common error JSON object for the user.
 */
@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    /**
     * This method handles input validation failures from @Valid annotations.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponse(ex, "Bad request: " + ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is a catch all for any unexpected internal exceptions.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        return buildResponse(ex,"Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadOperationException(BadRequestException ex) {
        return buildResponse(ex,"Bad Request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return buildResponse(ex,"Bad Request: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex) {
        return buildResponse(ex,"Conflict: " + ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnrecoverableException.class)
    protected ResponseEntity<Object> handleUnrecoverableException(UnrecoverableException ex){
        return buildResponse(ex,"Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildResponse(Exception ex, String message, HttpStatus status) {
        // Put this in the system logs
        logger.info("Got exception {} -> {}", ex.getClass().getSimpleName(), ex.getMessage());
        if (HttpStatus.INTERNAL_SERVER_ERROR == status) {
            logger.error("Since it was an internal server error here is the stack trace", ex);
        }

        // Build the response
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(message);
        return new ResponseEntity<>(errorDto, status);
    }
}
