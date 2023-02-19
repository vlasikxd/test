package com.bank.antifraud.mapper;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.supplier.SuspiciousCardTransferSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SuspiciousCardTransferMapperTest extends ParentTest {

    private static SuspiciousCardTransferMapper mapper;

    private static SuspiciousCardTransferEntity suspiciousCardTransfer;

    private static SuspiciousCardTransferDto suspiciousCardTransferDto;

    @BeforeAll
    static void init() {
        mapper = new SuspiciousCardTransferMapperImpl();

        var suspiciousCardTransferSupplier = new SuspiciousCardTransferSupplier();

        suspiciousCardTransfer = suspiciousCardTransferSupplier.getEntity(ONE, ONE, TRUE, TRUE, REASON, REASON);
        suspiciousCardTransferDto = suspiciousCardTransferSupplier.getDto(ONE, TWO, FALSE, FALSE, REASON, REASON);
    }

    @Test
    @DisplayName("маппинг к entity позитивный сценарий")
    void toEntityTest() {
        final SuspiciousCardTransferEntity result = mapper.toEntity(suspiciousCardTransferDto);

        assertAll(() -> {
            assertNull(result.getId());
            assertEquals(suspiciousCardTransferDto.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousCardTransferDto.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousCardTransferDto.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousCardTransferDto.getCardTransferId(), result.getCardTransferId());
            assertEquals(suspiciousCardTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("маппинг к entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг к dto позитивный сценарий")
    void toDtoTest() {
        final SuspiciousCardTransferDto result = mapper.toDto(suspiciousCardTransfer);

        assertAll(() -> {
            assertEquals(suspiciousCardTransfer.getId(), result.getId());
            assertEquals(suspiciousCardTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousCardTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousCardTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousCardTransfer.getCardTransferId(), result.getCardTransferId());
            assertEquals(suspiciousCardTransfer.getSuspiciousReason(), result.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("слияние в entity позитивный сценарий")
    void mergeToEntityTest() {
        final SuspiciousCardTransferEntity result = mapper.mergeToEntity(
                suspiciousCardTransferDto, suspiciousCardTransfer
        );

        assertAll(() -> {
            assertEquals(suspiciousCardTransfer.getId(), result.getId());
            assertEquals(suspiciousCardTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousCardTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousCardTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousCardTransfer.getCardTransferId(), result.getCardTransferId());
            assertEquals(suspiciousCardTransfer.getSuspiciousReason(), result.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("слияние в entity, где dto равен null")
    void mergeToEntityNullTest() {
        final SuspiciousCardTransferEntity result = mapper.mergeToEntity(
                null, suspiciousCardTransfer
        );

        assertAll(() -> {
            assertEquals(suspiciousCardTransfer.getId(), result.getId());
            assertEquals(suspiciousCardTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousCardTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousCardTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousCardTransfer.getCardTransferId(), result.getCardTransferId());
            assertEquals(suspiciousCardTransfer.getSuspiciousReason(), result.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto позитивный сценарий")
    void toListDtoTest() {
        final List<SuspiciousCardTransferDto> suspiciousCardTransfers = mapper.toListDto(
                List.of(suspiciousCardTransfer)
        );

        final SuspiciousCardTransferDto result = suspiciousCardTransfers.get(0);

        assertAll(() -> {
            assertEquals(ONE, suspiciousCardTransfers.size());
            assertEquals(suspiciousCardTransfer.getId(), result.getId());
            assertEquals(suspiciousCardTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousCardTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousCardTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousCardTransfer.getCardTransferId(), result.getCardTransferId());
            assertEquals(suspiciousCardTransfer.getSuspiciousReason(), result.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto, где список равен null")
    void toListDtoNullTest() {
        assertNull(mapper.toListDto(null));
    }

    @Test
    @DisplayName("маппинг к списку dto, где один из элементов списка равен null")
    void toListDtoElementNullTest() {
        final List<SuspiciousCardTransferEntity> suspiciousCardTransfers = new ArrayList<>();
        suspiciousCardTransfers.add(suspiciousCardTransfer);
        suspiciousCardTransfers.add(null);

        final List<SuspiciousCardTransferDto> actually = mapper.toListDto(suspiciousCardTransfers);
        final var zeroIndexResult = actually.get(0);

        assertAll(() -> {
            assertNull(actually.get(1));
            assertEquals(TWO, actually.size());
            assertEquals(suspiciousCardTransfer.getId(), zeroIndexResult.getId());
            assertEquals(suspiciousCardTransfer.getIsBlocked(), zeroIndexResult.getIsBlocked());
            assertEquals(suspiciousCardTransfer.getIsSuspicious(), zeroIndexResult.getIsSuspicious());
            assertEquals(suspiciousCardTransfer.getBlockedReason(), zeroIndexResult.getBlockedReason());
            assertEquals(suspiciousCardTransfer.getCardTransferId(), zeroIndexResult.getCardTransferId());
            assertEquals(suspiciousCardTransfer.getSuspiciousReason(), zeroIndexResult.getSuspiciousReason());
        });
    }
}
