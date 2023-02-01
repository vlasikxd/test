package com.bank.transfer.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

/**
 * Handler для {@link EntityNotFoundException}
 */
@Slf4j
@RestControllerAdvice
public class EntityNotFoundExceptionHandler {

    /**
     * @param exception {@link EntityNotFoundExceptionHandler}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(EntityNotFoundException.class)
    // TODO handleNotFound переименуй в handle. И как общий handler будет готов, этот handler выпилишь.
    public ResponseEntity<String> handleNotFound(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
