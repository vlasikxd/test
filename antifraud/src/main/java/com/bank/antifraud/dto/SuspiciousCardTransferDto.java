package com.bank.antifraud.dto;

import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO для сущности {@link SuspiciousCardTransferEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousCardTransferDto implements Serializable {

    Long id;

    @NotNull
    // TODO здесь должна лежать дто, а не лонг
    Long cardTransferId;

    @NotNull
    Boolean isBlocked;

    @NotNull
    Boolean isSuspicious;

    String blockedReason;

    @NotBlank
    String suspiciousReason;
}
