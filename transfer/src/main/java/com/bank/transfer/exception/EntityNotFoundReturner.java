// TODO exception переименуй в returner
package com.bank.transfer.exception;

import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

/**
 * Класс для создания исключения {@link EntityNotFoundException}
 */
@Component
public class EntityNotFoundReturner {

    /**
     * @param message {@link String}
     * @return {@link EntityNotFoundException}
     */
    public EntityNotFoundException getException(String message) {
        return new EntityNotFoundException(message);
    }
}
