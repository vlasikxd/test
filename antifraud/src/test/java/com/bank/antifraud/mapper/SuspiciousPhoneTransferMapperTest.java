package com.bank.antifraud.mapper;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.supplier.SuspiciousPhoneTransferSupplier;
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

class SuspiciousPhoneTransferMapperTest extends ParentTest {

    private static SuspiciousPhoneTransferMapper mapper;

    private static SuspiciousPhoneTransferEntity suspiciousPhoneTransfer;

    private static SuspiciousPhoneTransferDto suspiciousPhoneTransferDto;

    @BeforeAll
    static void init() {
        mapper = new SuspiciousPhoneTransferMapperImpl();

        var suspiciousPhoneTransferSupplier = new SuspiciousPhoneTransferSupplier();

        suspiciousPhoneTransfer = suspiciousPhoneTransferSupplier.getEntity(ONE, ONE, TRUE, TRUE, REASON, REASON);
        suspiciousPhoneTransferDto = suspiciousPhoneTransferSupplier.getDto(ONE, TWO, FALSE, FALSE, REASON, REASON);
    }

    @Test
    @DisplayName("маппинг к entity позитивный сценарий")
    void toEntityTest() {
        final SuspiciousPhoneTransferEntity result = mapper.toEntity(suspiciousPhoneTransferDto);

        assertAll(() -> {
            assertNull(result.getId());
            assertEquals(suspiciousPhoneTransferDto.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousPhoneTransferDto.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousPhoneTransferDto.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousPhoneTransferDto.getPhoneTransferId(), result.getPhoneTransferId());
            assertEquals(suspiciousPhoneTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
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
        final SuspiciousPhoneTransferDto result = mapper.toDto(suspiciousPhoneTransfer);

        assertAll(() -> {
            assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
            assertEquals(suspiciousPhoneTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousPhoneTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), result.getPhoneTransferId());
            assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), result.getSuspiciousReason());
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
        final SuspiciousPhoneTransferEntity result = mapper.mergeToEntity(
                suspiciousPhoneTransferDto, suspiciousPhoneTransfer
        );

        assertAll(() -> {
            assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
            assertEquals(suspiciousPhoneTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousPhoneTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), result.getPhoneTransferId());
            assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), result.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("слияние в entity, где dto равен null")
    void mergeToEntityNullTest() {
        final SuspiciousPhoneTransferEntity result = mapper.mergeToEntity(
                null, suspiciousPhoneTransfer
        );

        assertAll(() -> {
            assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
            assertEquals(suspiciousPhoneTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousPhoneTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), result.getPhoneTransferId());
            assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), result.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto позитивный сценарий")
    void toListDtoTest() {
        final List<SuspiciousPhoneTransferDto> suspiciousPhoneTransfers = mapper.toListDto(
                List.of(suspiciousPhoneTransfer)
        );

        final SuspiciousPhoneTransferDto result = suspiciousPhoneTransfers.get(0);

        assertAll(() -> {
            assertEquals(ONE, suspiciousPhoneTransfers.size());
            assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
            assertEquals(suspiciousPhoneTransfer.getIsBlocked(), result.getIsBlocked());
            assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(suspiciousPhoneTransfer.getBlockedReason(), result.getBlockedReason());
            assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), result.getPhoneTransferId());
            assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), result.getSuspiciousReason());
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
        final List<SuspiciousPhoneTransferEntity> suspiciousPhoneTransfers = new ArrayList<>();
        suspiciousPhoneTransfers.add(suspiciousPhoneTransfer);
        suspiciousPhoneTransfers.add(null);

        final List<SuspiciousPhoneTransferDto> actually = mapper.toListDto(suspiciousPhoneTransfers);
        final var zeroIndexResult = actually.get(0);

        assertAll(() -> {
            assertNull(actually.get(1));
            assertEquals(TWO, actually.size());
            assertEquals(suspiciousPhoneTransfer.getId(), zeroIndexResult.getId());
            assertEquals(suspiciousPhoneTransfer.getIsBlocked(), zeroIndexResult.getIsBlocked());
            assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), zeroIndexResult.getIsSuspicious());
            assertEquals(suspiciousPhoneTransfer.getBlockedReason(), zeroIndexResult.getBlockedReason());
            assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), zeroIndexResult.getPhoneTransferId());
            assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), zeroIndexResult.getSuspiciousReason());
        });
    }
}
