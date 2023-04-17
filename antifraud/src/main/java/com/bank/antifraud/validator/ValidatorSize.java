package com.bank.antifraud.validator;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class ValidatorSize {
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
