package com.bank.publicinfo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

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

    protected String LocalTimeToString(LocalTime time) {
        return time.toString();
    }
}
