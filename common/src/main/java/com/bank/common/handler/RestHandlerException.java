package com.bank.common.handler;

import com.bank.common.exception.ClientAccessDeniedException;
import com.bank.common.exception.ValidationException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link NotFoundException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link IllegalStateException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleIllegalStateException(IllegalStateException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link IllegalArgumentException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link ValidationException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleValidationException(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link UnsupportedOperationException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleUnsupportedOperationException(UnsupportedOperationException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link HttpMessageNotReadableException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link MaxUploadSizeExceededException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.LENGTH_REQUIRED)
    @Hidden
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link ClientAccessDeniedException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(ClientAccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleClientAccessDeniedException(ClientAccessDeniedException exception) {
        log.error(exception.getMessage(), exception);
        return exception.getMessage();
    }

    /**
     * @param exception {@link Exception}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerGlobalException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return "Ошибка на стороне сервера.";
    }

    /**
     * * @param exception {@link MethodArgumentTypeMismatchException}
     * @return {@link ResponseEntity<String>}
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        return "Некорректно указан id";
    }
}
