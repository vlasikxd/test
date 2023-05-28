package com.bank.antifraud.dto;

import com.bank.antifraud.dto.transferDto.AccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
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
 * DTO для сущности {@link SuspiciousAccountTransferEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousAccountTransferDto implements Serializable {
    Long id;

    @NotNull
    AccountTransferDto accountTransferId;
    @NotNull
    Boolean isBlocked;
    @NotNull
    Boolean isSuspicious;
    String blockedReason;
    @NotBlank
    String suspiciousReason;
}
