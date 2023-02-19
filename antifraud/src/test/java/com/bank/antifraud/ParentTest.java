package com.bank.antifraud;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {

    protected static final Long ONE = 1L;
    protected static final Long TWO = 2L;
    protected static final String REASON = "just cause";
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
