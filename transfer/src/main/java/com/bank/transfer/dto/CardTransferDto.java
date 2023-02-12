package com.bank.transfer.dto;

import com.bank.transfer.entity.CardTransferEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 * ДТО для {@link CardTransferEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardTransferDto {
    Long id;
    Long cardNumber;
    BigDecimal amount;
    String purpose;
    Long accountDetailsId;
}
