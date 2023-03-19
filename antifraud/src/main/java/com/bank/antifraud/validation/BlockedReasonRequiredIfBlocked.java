package com.bank.antifraud.validation;

import com.bank.antifraud.validation.impl.BlockedReasonValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BlockedReasonValidator.class})
public @interface BlockedReasonRequiredIfBlocked {
    String message() default "Blocked reason cannot be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

