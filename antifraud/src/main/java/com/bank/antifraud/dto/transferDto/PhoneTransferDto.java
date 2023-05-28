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
public class PhoneTransferDto {
    Long id;
    Long phoneNumber;
    BigDecimal amount;
    String purpose;
    Long accountDetailsId;
}
