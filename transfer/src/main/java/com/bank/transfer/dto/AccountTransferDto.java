package com.bank.transfer.dto;

import com.bank.transfer.entity.AccountTransferEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * ДТО для {@link AccountTransferEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountTransferDto {
    Long id;
    Long accountNumber;
    BigDecimal amount;
    String purpose;
    Long accountDetailsId;
}
