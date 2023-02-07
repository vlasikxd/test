package com.bank.antifraud.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

/**
 * Сравнивает размеры списков
 */
@Component
public class ListSizeValidator {

    /**
     * Выбрасывает исключение, если размер списков разный
     * @param listA {@link List}
     * @param listB {@link List}
     * @param exceptionSupplier {@link RuntimeException}
     */
    public void throwIfDifferent(List<?> listA, List<?> listB,
                                 Supplier<? extends RuntimeException> exceptionSupplier) {
        if (listA.size() != listB.size()) {
            throw exceptionSupplier.get();
        }
    }
}
