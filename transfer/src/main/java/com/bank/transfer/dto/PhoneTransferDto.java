package com.bank.transfer.dto;

import com.bank.transfer.entity.PhoneTransferEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * ДТО для {@link PhoneTransferEntity}
 */
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
