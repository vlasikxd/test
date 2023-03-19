package com.bank.antifraud.validation.impl;

import com.bank.antifraud.validation.BlockedReasonRequiredIfBlocked;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Component
public class BlockedReasonValidator implements ConstraintValidator<BlockedReasonRequiredIfBlocked, Object> {

    private Field isBlockedField;
    private Field blockedReasonField;
    private Boolean isBlocked;
    private String blockedReason;

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            isBlockedField = obj.getClass().getDeclaredField("isBlocked");
            blockedReasonField = obj.getClass().getDeclaredField("blockedReason");

            isBlockedField.setAccessible(true);
            blockedReasonField.setAccessible(true);

            isBlocked = (Boolean) isBlockedField.get(obj);
            blockedReason = (String) blockedReasonField.get(obj);

            if (isBlocked && StringUtils.isBlank(blockedReason)) {
                return false;
            }
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
