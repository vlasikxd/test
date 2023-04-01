package com.bank.profile.validator;

import org.springframework.stereotype.Component;

import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import javax.validation.Validation;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Метод для валидации полей DTO
 */
@Component
public class DtoValidator<T> {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    /**
     * Выбрасывает исключение, если не прошла валидация поля(ей)
     *
     * @param dto {@link List}
     * @param exceptionSupplier {@link RuntimeException}
     */
    public void validate(T dto, Supplier<? extends RuntimeException> exceptionSupplier) {

        final Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw exceptionSupplier.get();
        }
    }
}
