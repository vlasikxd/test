package com.bank.profile;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {

    protected static final Long ONE = 1L;
    protected static final Long TWO = 2L;
    protected static final Integer INT_ONE = 1;
    protected static final String WHITESPACE = " ";
    protected static final LocalDate LOCAL_DATE = LocalDate.now();

    protected int getIntFromLong(Long number) {
        return number.intValue();
    }

    protected String LocalDateToString(LocalDate date) {
        return date.toString();
    }
}
