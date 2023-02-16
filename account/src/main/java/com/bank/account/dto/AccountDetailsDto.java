package com.bank.account.dto;

import com.bank.account.entity.AccountDetailsEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * Dto для {@link AccountDetailsEntity}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailsDto {
    Long id;
    Long passportId;
    Long accountNumber;
    Long bankDetailsId;
    BigDecimal money;
    Boolean negativeBalance;
    Long profileId;
}
