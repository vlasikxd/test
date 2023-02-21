package com.bank.profile.validator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

/**
 * Методы для валидации результатов запросов к БД
 */
@Component
public class EntityListValidator {

    /**
     * Выбрасывает исключение, если размер списков разный
     * @param entities {@link List}
     * @param ids {@link List}
     * @param exceptionSupplier {@link RuntimeException}
     */
    public void checkSize(List<?> entities, List<?> ids,
                                 Supplier<? extends RuntimeException> exceptionSupplier) {
        if (entities.size() != ids.size()) {
            throw exceptionSupplier.get();
        }
    }
}
