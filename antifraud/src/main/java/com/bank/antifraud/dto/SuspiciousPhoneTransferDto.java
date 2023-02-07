package com.bank.antifraud.dto;

import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO для сущности {@link SuspiciousPhoneTransferEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousPhoneTransferDto implements Serializable {

    Long id;

    @NotNull
    Long phoneTransferId;

    @NotNull
    Boolean isBlocked;

    @NotNull
    Boolean isSuspicious;

    String blockedReason;

    @NotNull
    String suspiciousReason;
}
