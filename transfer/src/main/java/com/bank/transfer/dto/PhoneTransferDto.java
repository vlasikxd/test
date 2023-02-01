package com.bank.transfer.dto;

import com.bank.transfer.entity.PhoneTransferEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * ДТО для {@link PhoneTransferEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneTransferDto {

    private Long id;
    private Long phoneNumber;
    private BigDecimal amount;
    private String purpose;
    private Long accountDetailsId;
}
