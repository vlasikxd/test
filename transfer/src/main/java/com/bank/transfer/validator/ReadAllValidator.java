package com.bank.transfer.validator;

import com.bank.transfer.returner.EntityNotFoundReturner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для валидации метода readAll
 */
@Component
@RequiredArgsConstructor
public class ReadAllValidator {
    private final EntityNotFoundReturner returner;

    /**
     * @param dtoList лист дто
     * @param ids лист технических идентификаторов сущностей.
     * @param message сообщение об исключении.
     */
    public <T> void validation(List<T> dtoList, List<Long> ids, String message) {
        if (dtoList.size() != ids.size()) {
            throw returner.getException(message);
        }
    }
}
