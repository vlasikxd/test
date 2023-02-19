package com.bank.antifraud.supplier;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;

public class SuspiciousCardTransferSupplier {

    public SuspiciousCardTransferEntity getEntity(Long id, Long accountTransferId, Boolean isBlocked,
                                                  Boolean isSuspicious, String blockedReason, String suspiciousReason) {

        return new SuspiciousCardTransferEntity(id, accountTransferId, isBlocked,
                isSuspicious, blockedReason, suspiciousReason
        );
    }

    public SuspiciousCardTransferDto getDto(Long id, Long accountTransferId, Boolean isBlocked,
                                            Boolean isSuspicious, String blockedReason, String suspiciousReason) {

        return new SuspiciousCardTransferDto(id, accountTransferId, isBlocked,
                isSuspicious, blockedReason, suspiciousReason
        );
    }
}
