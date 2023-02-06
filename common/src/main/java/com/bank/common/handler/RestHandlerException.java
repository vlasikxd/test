package com.bank.common.handler;

import com.bank.common.exception.ClientAccessDeniedException;
import com.bank.common.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;

/**
 * Обработчик исключений.
 */
@Slf4j
@RestControllerAdvice
public class RestHandlerException {

    /**
     * @param exception {@link EntityNotFoundException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * @param exception {@link NotFoundException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * @param exception {@link IllegalStateException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    /**
     * @param exception {@link IllegalArgumentException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    /**
     * @param exception {@link ValidationException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    /**
     * @param exception {@link UnsupportedOperationException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperationException(UnsupportedOperationException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exception.getMessage());
    }

    /**
     * @param exception {@link HttpMessageNotReadableException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    /**
     * @param exception {@link MaxUploadSizeExceededException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(exception.getMessage());
    }

    /**
     * @param exception {@link ClientAccessDeniedException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(ClientAccessDeniedException.class)
    public ResponseEntity<String> handleClientAccessDeniedException(ClientAccessDeniedException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    /**
     * @param exception {@link Exception}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerGlobalException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка на стороне сервера.");
    }
}
