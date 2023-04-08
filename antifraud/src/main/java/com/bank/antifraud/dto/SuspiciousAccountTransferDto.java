package com.bank.antifraud.dto;

import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.validation.BlockedReasonRequiredIfBlocked;
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
@BlockedReasonRequiredIfBlocked
public class SuspiciousAccountTransferDto implements Serializable {

    Long id;

    @NotNull
    Long accountTransferId;

    @NotNull
    Boolean isBlocked;

    @NotNull
    Boolean isSuspicious;

    String blockedReason;

//    TODO  не нужна это аннотация
    @NotBlank
    String suspiciousReason;
}
