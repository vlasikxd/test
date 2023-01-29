package com.bank.account.dto;

import com.bank.account.entity.AccountDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Dto для {@link AccountDetailsEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDto {
    // TODO в этом пакете сделай AuditDto.
    private Long id;
    private Long passportId;
    private Long accountNumber;
    private Long bankDetailsId;
    private BigDecimal money;
    private Boolean negativeBalance;
    private Long profileId;
}
