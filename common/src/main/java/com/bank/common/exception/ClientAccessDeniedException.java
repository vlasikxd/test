package com.bank.common.exception;

/**
 * Исключение при запрете на допуск со стороны клиента.
 */
public class ClientAccessDeniedException extends RuntimeException {

    public ClientAccessDeniedException(String message) {
        super(message);
    }
}
