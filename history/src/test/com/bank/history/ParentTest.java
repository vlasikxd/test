package com.bank.history;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {

    protected static final Long ONE = 1L;
    protected static final Long TWO = 2L;
    protected static final Long THREE = 3L;
    protected static HistoryDto getHistoryDto(Long id, Long transferAuditId, Long profileAuditId,
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

    protected static HistoryEntity getHistoryEntity(Long id, Long transferAuditId, Long profileAuditId,
                                              Long accountAuditId, Long antiFraudAuditId,
                                              Long publicBankInfoAuditId, Long authorizationAuditId) {

        return new HistoryEntity(id, transferAuditId, profileAuditId,
                 accountAuditId,  antiFraudAuditId,
                 publicBankInfoAuditId, authorizationAuditId);
    }

    protected static List<HistoryEntity> getHistories(HistoryEntity ... histories) {
        return List.of(histories);
    }

    protected HistoryEntity getZeroEntityElement(List<HistoryEntity> result) {
        return result.get(0);
    }

    protected HistoryDto getZeroElement(List<HistoryDto> result) {
        return result.get(0);
    }
}
