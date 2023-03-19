package com.bank.antifraud.supplier;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;

public class SuspiciousAccountTransferSupplier {

    private final Boolean isBlocked = false;

    private final String blockedReason = "Random blocked reason";

    public SuspiciousAccountTransferEntity getEntity(Long id, Long accountTransferId,
                                                     Boolean isSuspicious, String suspiciousReason) {
        return new SuspiciousAccountTransferEntity(id, accountTransferId, isBlocked, isSuspicious,
                blockedReason, suspiciousReason
        );
    }

    public SuspiciousAccountTransferDto getDto(Long id, Long accountTransferId,
                                               Boolean isSuspicious, String suspiciousReason) {
        return new SuspiciousAccountTransferDto(id, accountTransferId, isBlocked, isSuspicious,
                blockedReason, suspiciousReason
        );
    }
}
