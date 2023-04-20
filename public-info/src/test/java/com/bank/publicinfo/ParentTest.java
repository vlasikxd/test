package com.bank.publicinfo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {

    protected static final Long ONE = 1L;
    protected static final Long TWO = 2L;
    protected static final Long THREE = 3L;
    protected static final Byte BYTE = 1;
    protected static final String SPACE = " ";

    protected int getIntFromLong(Long number) {
        return number.intValue();
    }

    protected int getIntFromByte(Byte value) {
        return value.intValue();
    }

    protected String formatLocalTime(LocalTime localTime) {
        return localTime.format(
                DateTimeFormatter.ISO_TIME
        );
    }

    protected String toStringLocalTime(LocalTime localTime) {
        return localTime.toString();
    }
}
