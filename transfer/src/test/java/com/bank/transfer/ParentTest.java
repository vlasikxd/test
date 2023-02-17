package com.bank.transfer;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {
    protected static final Long ID = 15L;
    protected static final Long ENTITY_NUMBER = 15L;
    protected static final BigDecimal AMOUNT = BigDecimal.valueOf(15);
    protected static final String PURPOSE = "Tarzan";
    protected static final Long ENTITY_DETAILS_ID = 15L;
}
