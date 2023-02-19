package com.bank.antifraud.supplier;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;

public class SuspiciousAccountTransferSupplier {

    public SuspiciousAccountTransferEntity getEntity(Long id, Long accountTransferId, Boolean isBlocked,
                                                     Boolean isSuspicious, String blockedReason,
                                                     String suspiciousReason) {

        return new SuspiciousAccountTransferEntity(id, accountTransferId, isBlocked,
                isSuspicious, blockedReason, suspiciousReason
        );
    }

    public SuspiciousAccountTransferDto getDto(Long id, Long accountTransferId, Boolean isBlocked,
                                               Boolean isSuspicious, String blockedReason, String suspiciousReason) {

        return new SuspiciousAccountTransferDto(id, accountTransferId, isBlocked,
                isSuspicious, blockedReason, suspiciousReason
        );
    }
}
