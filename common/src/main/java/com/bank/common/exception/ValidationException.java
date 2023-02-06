package com.bank.common.exception;

/**
 * Исключение ошибок валидации данных.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
