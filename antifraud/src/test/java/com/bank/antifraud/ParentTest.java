package com.bank.antifraud;

import com.bank.antifraud.mapper.MapperSupplier;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest implements MapperSupplier {

    protected static final Double ONE_AND_HALF = 1.5;

    protected final static String SUSPICIOUS_CARD_TRANSFER_NAME = "SuspiciousCardTransfer";
    protected final static String SUSPICIOUS_PHONE_TRANSFER_NAME = "SuspiciousPhoneTransfer";
    protected final static String SUSPICIOUS_ACCOUNT_TRANSFER_NAME = "SuspiciousAccountTransfer";

    protected String getNotFoundExceptionMessage(Long id, String entityName) {
        return entityName + " с id = " + id + " не найден.";
    }

    protected int getIntFromLong(Long number) {
        return number.intValue();
    }
}
