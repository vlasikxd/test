package com.bank.antifraud.supplier;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.dto.transferDto.PhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;

public class SuspiciousPhoneTransferSupplier {

    private final String blockedReason = "Random blocked reason";
    private final String suspiciousReason = "Random suspicious reason";

    public SuspiciousPhoneTransferEntity getEntity(Long id, Long accountTransferId,
                                                   Boolean isBlocked, Boolean isSuspicious) {
        return new SuspiciousPhoneTransferEntity(id, accountTransferId, isBlocked, isSuspicious,
                blockedReason, suspiciousReason
        );
    }

    public SuspiciousPhoneTransferDto getDto(Long id, Long phoneTransferId,
                                             Boolean isBlocked, Boolean isSuspicious) {
        return new SuspiciousPhoneTransferDto(id, new PhoneTransferDto(phoneTransferId, null, null, null, null), isBlocked, isSuspicious,
                blockedReason, suspiciousReason
        );
    }
}
