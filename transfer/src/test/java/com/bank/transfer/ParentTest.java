package com.bank.transfer;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {
    protected static final Long id = 15L;
    protected static final Long accountNumber = 15L;
    protected static final BigDecimal amount = BigDecimal.valueOf(15);
    protected static final String purpose = "Tarzan";
    protected static final Long accountDetailsId = 15L;





}
