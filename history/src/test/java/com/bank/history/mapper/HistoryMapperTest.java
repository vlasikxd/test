package com.bank.history.mapper;

import com.bank.history.ParentTest;
import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.bank.history.supplier.HistorySupplier.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HistoryMapperTest extends ParentTest {

    private HistoryMapper mapper;

    private HistoryEntity historyEntity;

    private HistoryDto historyDto;
    private HistoryDto updatedHistoryDto;

    private List<HistoryEntity> histories;

    @BeforeEach
    void init() {
        mapper = new HistoryMapperImpl();

        historyDto = getHistoryDto(ONE, ONE, TWO, THREE, TWO, THREE, ONE);

        updatedHistoryDto = getHistoryDto(THREE, THREE, TWO, THREE, TWO, THREE, TWO);

        historyEntity = getHistoryEntity(ONE, ONE, ONE, ONE, ONE, ONE, ONE);

        HistoryEntity historyEntityUpdate = getHistoryEntity(ONE, ONE, TWO, ONE, ONE, TWO, THREE);

        histories = getHistories(historyEntity, historyEntityUpdate);
    }

    @Test
    @DisplayName("маппинг в entity, позитивный сценарий")
    void toEntityTest() {
        final HistoryEntity result = mapper.toEntity(historyDto);

        assertAll(() -> {
            assertNotEquals(historyDto.getId(), result.getId());
            assertEquals(historyDto.getPublicBankInfoAuditId(), result.getPublicBankInfoAuditId());
            assertEquals(historyDto.getProfileAuditId(), result.getProfileAuditId());
            assertEquals(historyDto.getAntiFraudAuditId(), result.getAntiFraudAuditId());
            assertEquals(historyDto.getAuthorizationAuditId(), result.getAuthorizationAuditId());
            assertEquals(historyDto.getAccountAuditId(), result.getAccountAuditId());
            assertEquals(historyDto.getTransferAuditId(), result.getTransferAuditId());
        });
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг в dto, позитивный сценарий")
    void toDtoTest() {
        final HistoryDto result = mapper.toDto(historyEntity);

        assertAll(() -> {
            assertEquals(historyEntity.getId(), result.getId());
            assertEquals(historyEntity.getPublicBankInfoAuditId(), result.getPublicBankInfoAuditId());
            assertEquals(historyEntity.getProfileAuditId(), result.getProfileAuditId());
            assertEquals(historyEntity.getAntiFraudAuditId(), result.getAntiFraudAuditId());
            assertEquals(historyEntity.getAuthorizationAuditId(), result.getAuthorizationAuditId());
            assertEquals(historyEntity.getAccountAuditId(), result.getAccountAuditId());
            assertEquals(historyEntity.getTransferAuditId(), result.getTransferAuditId());
        });
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("маппинг в entity, позитивный сценарий")
    void mergeToEntityTest() {
        final HistoryEntity result = mapper.mergeToEntity(updatedHistoryDto, historyEntity);

        assertAll(() -> {
            assertNotEquals(updatedHistoryDto.getId(), result.getId());
            assertEquals(historyEntity.getPublicBankInfoAuditId(), result.getPublicBankInfoAuditId());
            assertEquals(historyEntity.getProfileAuditId(), result.getProfileAuditId());
            assertEquals(historyEntity.getAntiFraudAuditId(), result.getAntiFraudAuditId());
            assertEquals(historyEntity.getAuthorizationAuditId(), result.getAuthorizationAuditId());
            assertEquals(historyEntity.getAccountAuditId(), result.getAccountAuditId());
            assertEquals(historyEntity.getTransferAuditId(), result.getTransferAuditId());
        });
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final HistoryEntity result = mapper.mergeToEntity(null, historyEntity);

        assertAll(() -> {
            assertEquals(ONE, result.getId());
            assertEquals(ONE, result.getAuthorizationAuditId());
            assertEquals(ONE, result.getPublicBankInfoAuditId());
            assertEquals(ONE, result.getTransferAuditId());
            assertEquals(ONE, result.getProfileAuditId());
            assertEquals(ONE, result.getAntiFraudAuditId());
            assertEquals(ONE, result.getAccountAuditId());
        });
    }

    @Test
    @DisplayName("маппинг списка entity в список dto, позитивный сценарий")
    void toDtoListTest() {
        final List<HistoryDto> result = mapper.toDtoList(histories);

        assertAll(() -> {
            assertEquals(histories.size(), result.size());
            assertEquals(getZeroEntityElement(histories).getId(), getZeroElement(result).getId());
            assertEquals(getZeroEntityElement(histories).getAccountAuditId(), getZeroElement(result).getAccountAuditId());

            assertEquals(getZeroEntityElement(histories).getTransferAuditId(),
                    getZeroElement(result).getTransferAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getPublicBankInfoAuditId(),
                    getZeroElement(result).getPublicBankInfoAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getProfileAuditId(),
                    getZeroElement(result).getProfileAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getAuthorizationAuditId(),
                    getZeroElement(result).getAuthorizationAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getAntiFraudAuditId(),
                    getZeroElement(result).getAntiFraudAuditId()
            );
        });
    }

    @Test
    @DisplayName("маппинг списка entity в список dto, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("маппинг списка entity в список dto, один элемент списка null")
    void toDtoListOneElementNullTest() {
        final List<HistoryEntity> severalAccountDetails = new ArrayList<>();
        severalAccountDetails.add(historyEntity);
        severalAccountDetails.add(null);

        final List<HistoryDto> result = mapper.toDtoList(severalAccountDetails);

        assertAll(() -> {
            assertNull(result.get(1));
            assertEquals(histories.size(), result.size());
            assertEquals(getZeroEntityElement(histories).getId(), getZeroElement(result).getId());
            assertEquals(getZeroEntityElement(histories).getAntiFraudAuditId(), getZeroElement(result).getAntiFraudAuditId());

            assertEquals(getZeroEntityElement(histories).getTransferAuditId(),
                    getZeroElement(result).getTransferAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getAccountAuditId(),
                    getZeroElement(result).getAccountAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getProfileAuditId(),
                    getZeroElement(result).getProfileAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getAuthorizationAuditId(),
                    getZeroElement(result).getAuthorizationAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getPublicBankInfoAuditId(),
                    getZeroElement(result).getPublicBankInfoAuditId()
            );
        });
    }
}
