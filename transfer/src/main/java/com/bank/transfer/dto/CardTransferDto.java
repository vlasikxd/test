package com.bank.transfer.dto;

import com.bank.transfer.entity.CardTransferEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * ДТО для {@link CardTransferEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardTransferDto {

    private Long id;
    private Long cardNumber;
    private BigDecimal amount;
    private String purpose;
    private Long accountDetailsId;
}
