package com.bank.antifraud.supplier;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;

public class SuspiciousPhoneTransferSupplier {

    public SuspiciousPhoneTransferEntity getEntity(Long id, Long accountTransferId, Boolean isBlocked,
                                                   Boolean isSuspicious, String blockedReason,
                                                   String suspiciousReason) {

        return new SuspiciousPhoneTransferEntity(id, accountTransferId, isBlocked,
                isSuspicious, blockedReason, suspiciousReason
        );
    }

    public SuspiciousPhoneTransferDto getDto(Long id, Long accountTransferId, Boolean isBlocked,
                                             Boolean isSuspicious, String blockedReason, String suspiciousReason) {

        return new SuspiciousPhoneTransferDto(id, accountTransferId, isBlocked,
                isSuspicious, blockedReason, suspiciousReason
        );
    }
}
