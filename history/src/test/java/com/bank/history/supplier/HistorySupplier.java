package com.bank.history.supplier;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;

import java.util.List;

public class HistorySupplier {
    public static HistoryDto getHistoryDto(Long id, Long transferAuditId, Long profileAuditId,
                                           Long accountAuditId, Long antiFraudAuditId,
                                           Long publicBankInfoAuditId, Long authorizationAuditId) {

        return HistoryDto.builder()
                .id(id)
                .transferAuditId(transferAuditId)
                .profileAuditId(profileAuditId)
                .accountAuditId(accountAuditId)
                .antiFraudAuditId(antiFraudAuditId)
                .publicBankInfoAuditId(publicBankInfoAuditId)
                .authorizationAuditId(authorizationAuditId)
                .build();
    }

    public static HistoryEntity getHistoryEntity(Long id, Long transferAuditId, Long profileAuditId,
                                                 Long accountAuditId, Long antiFraudAuditId,
                                                 Long publicBankInfoAuditId, Long authorizationAuditId) {

        return new HistoryEntity(id, transferAuditId, profileAuditId,
                accountAuditId,  antiFraudAuditId,
                publicBankInfoAuditId, authorizationAuditId);
    }

    public static List<HistoryEntity> getHistories(HistoryEntity... histories) {
        return List.of(histories);
    }

    public static HistoryEntity getZeroEntityElement(List<HistoryEntity> result) {
        return result.get(0);
    }

    public static HistoryDto getZeroElement(List<HistoryDto> result) {
        return result.get(0);
    }
}
