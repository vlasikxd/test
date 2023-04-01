package com.bank.profile;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {

    protected static final Long ONE = 1L;
    protected static final Long TWO = 2L;
    protected static final Long NUMBER = 123456L;
    protected static final Long PHONE_NUMBER = 1234567890L;
    protected static final Long INN = 123456789012L;
    protected static final Long SNILS = 12345678901L;
    protected static final Integer INT_ONE = 1234;
    protected static final Integer INT_TWO = 123456;
    protected static final String WHITESPACE = "Оk";
    protected static final String GENDER = "муж";
    protected static final String EMAIL = "test@gmail.com";
    protected static final LocalDate LOCAL_DATE = LocalDate.of(1990, 9, 3);

    protected int getIntFromLong(Long number) {
        return number.intValue();
    }

    protected String LocalDateToString(LocalDate date) {
        return date.toString();
    }
}
