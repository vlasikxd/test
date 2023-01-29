package com.bank.authorization.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

/**
 * Handler для {@link EntityNotFoundException}.
 */
@Slf4j
@RestControllerAdvice
public class EntityNotFoundExceptionHandler {

    /**
     * @param exception {@link EntityNotFoundException}.
     * @return {@link ResponseEntity<String>}.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> onEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(404).body(exception.getMessage());
    }
}
