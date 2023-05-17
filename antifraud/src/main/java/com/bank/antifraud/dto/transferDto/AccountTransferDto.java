package com.bank.antifraud.dto.transferDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

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
