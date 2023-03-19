package com.bank.antifraud.supplier;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;

public class SuspiciousCardTransferSupplier {

    private final String blockedReason = "Random blocked reason";
    private final String suspiciousReason = "Random suspicious reason";

    public SuspiciousCardTransferEntity getEntity(Long id, Long accountTransferId,
                                                  Boolean isBlocked, Boolean isSuspicious) {
        return new SuspiciousCardTransferEntity(id, accountTransferId, isBlocked, isSuspicious,
                blockedReason, suspiciousReason
        );
    }

    public SuspiciousCardTransferDto getDto(Long id, Long accountTransferId,
                                            Boolean isBlocked, Boolean isSuspicious) {
        return new SuspiciousCardTransferDto(id, accountTransferId, isBlocked, isSuspicious,
                blockedReason, suspiciousReason
        );
    }
}
