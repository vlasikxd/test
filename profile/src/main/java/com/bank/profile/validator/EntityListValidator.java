package com.bank.profile.validator;

import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Методы для валидации результатов запросов к БД
 */
@Component
public class EntityListValidator {

    /**
     * @param entities список Entity.
     * @param ids список технических идентификаторов.
     * @param message сообщение об ошибке.
     */
    public void checkSize(List<?> entities, List<?> ids, String message) {
        if (entities.size() != ids.size()) {
            throw returnEntityNotFoundException(message);
        }
    }

    /**
     * @param message сообщение об ошибке.
     * @return {@link EntityNotFoundException}.
     */
    public EntityNotFoundException returnEntityNotFoundException(String message) {
        return new EntityNotFoundException(message);
    }
}
