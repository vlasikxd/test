package com.bank.antifraud.validator;

import com.bank.common.exception.ValidationException;

public class AccountValidator {
    /**
     * Выбрасывает исключение, isBlocked = true и blockedReason пустая
     * @param isBlocked {@link boolean}
     * @param blockedReason {@link String}
     */
    public static void dtoValidator(boolean isBlocked, String blockedReason)  {
        if (isBlocked & blockedReason == null)  {
            throw new ValidationException("Сохранение невозможно, должна быть причина блокировки!");
        }
    }
}
