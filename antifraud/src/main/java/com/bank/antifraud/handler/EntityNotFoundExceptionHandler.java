package com.bank.antifraud.handler;

import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler для {@link EntityNotFoundException}
 */
@Slf4j
@RestControllerAdvice
public class EntityNotFoundExceptionHandler {

    /**
     * @param exception {@link EntityNotFoundException}
     * @return {@link ResponseEntity} c статусом HttpStatus.NOT_FOUND
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
