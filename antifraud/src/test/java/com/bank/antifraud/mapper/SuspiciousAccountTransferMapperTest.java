package com.bank.antifraud.mapper;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.supplier.SuspiciousAccountTransferSupplier;
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

class SuspiciousAccountTransferMapperTest extends ParentTest {

    private static SuspiciousAccountTransferMapper mapper;

    private static SuspiciousAccountTransferEntity suspiciousAccountTransfer;

    private static SuspiciousAccountTransferDto suspiciousAccountTransferDto;

    @BeforeAll
    static void init() {
        mapper = new SuspiciousAccountTransferMapperImpl();

        var suspiciousAccountTransferSupplier = new SuspiciousAccountTransferSupplier();

        suspiciousAccountTransfer = suspiciousAccountTransferSupplier.getEntity(ONE, ONE, TRUE, TRUE, REASON, REASON);
        suspiciousAccountTransferDto = suspiciousAccountTransferSupplier.getDto(ONE, TWO, FALSE, FALSE, REASON, REASON);
    }

    @Test
    @DisplayName("маппинг к entity позитивный сценарий")
    void toEntityTest() {
        final SuspiciousAccountTransferEntity result = mapper.toEntity(suspiciousAccountTransferDto);

        assertAll(() -> {
            assertNull(result.getId());
            assertEquals(suspiciousAccountTransferDto.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousAccountTransferDto.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousAccountTransferDto.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousAccountTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
            assertEquals(suspiciousAccountTransferDto.getAccountTransferId(), result.getAccountTransferId());
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
        final SuspiciousAccountTransferDto result = mapper.toDto(suspiciousAccountTransfer);

        assertAll(() -> {
            assertEquals(suspiciousAccountTransfer.getId(), result.getId());
            assertEquals(suspiciousAccountTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousAccountTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousAccountTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), result.getSuspiciousReason());
            assertEquals(suspiciousAccountTransfer.getAccountTransferId(), result.getAccountTransferId());
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
        final SuspiciousAccountTransferEntity result = mapper.mergeToEntity(
                suspiciousAccountTransferDto, suspiciousAccountTransfer
        );

        assertAll(() -> {
            assertEquals(suspiciousAccountTransfer.getId(), result.getId());
            assertEquals(suspiciousAccountTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousAccountTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousAccountTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), result.getSuspiciousReason());
            assertEquals(suspiciousAccountTransfer.getAccountTransferId(), result.getAccountTransferId());
        });
    }

    @Test
    @DisplayName("слияние в entity, где dto равен null")
    void mergeToEntityNullTest() {
        final SuspiciousAccountTransferEntity result = mapper.mergeToEntity(
                null, suspiciousAccountTransfer
        );

        assertAll(() -> {
            assertEquals(suspiciousAccountTransfer.getId(), result.getId());
            assertEquals(suspiciousAccountTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousAccountTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousAccountTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), result.getSuspiciousReason());
            assertEquals(suspiciousAccountTransfer.getAccountTransferId(), result.getAccountTransferId());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto позитивный сценарий")
    void toListDtoTest() {
        final List<SuspiciousAccountTransferDto> suspiciousAccountTransfers = mapper.toListDto(
                List.of(suspiciousAccountTransfer)
        );

        final SuspiciousAccountTransferDto result = suspiciousAccountTransfers.get(0);

        assertAll(() -> {
            assertEquals(ONE, suspiciousAccountTransfers.size());
            assertEquals(suspiciousAccountTransfer.getId(), result.getId());
            assertEquals(suspiciousAccountTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousAccountTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousAccountTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), result.getSuspiciousReason());
            assertEquals(suspiciousAccountTransfer.getAccountTransferId(), result.getAccountTransferId());
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
        final List<SuspiciousAccountTransferEntity> suspiciousAccountTransfers = new ArrayList<>();
        suspiciousAccountTransfers.add(suspiciousAccountTransfer);
        suspiciousAccountTransfers.add(null);

        final List<SuspiciousAccountTransferDto> actually = mapper.toListDto(suspiciousAccountTransfers);
        final var zeroIndexResult = actually.get(0);

        assertAll(() -> {
            assertNull(actually.get(1));
            assertEquals(TWO, actually.size());
            assertEquals(suspiciousAccountTransfer.getId(), zeroIndexResult.getId());
            assertEquals(suspiciousAccountTransfer.getIsBlocked(), zeroIndexResult.getIsBlocked());
            assertEquals(suspiciousAccountTransfer.getIsSuspicious(), zeroIndexResult.getIsSuspicious());
            assertEquals(suspiciousAccountTransfer.getBlockedReason(), zeroIndexResult.getBlockedReason());
            assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), zeroIndexResult.getSuspiciousReason());
            assertEquals(suspiciousAccountTransfer.getAccountTransferId(), zeroIndexResult.getAccountTransferId());
        });
    }
}
