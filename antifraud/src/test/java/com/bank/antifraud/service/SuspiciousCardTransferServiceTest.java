package com.bank.antifraud.service;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.transferDto.CardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.mapper.SuspiciousCardTransferMapperImpl;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.impl.SuspiciousCardTransferServiceImpl;
import com.bank.antifraud.supplier.SuspiciousCardTransferSupplier;
import com.bank.antifraud.validator.ValidatorSize;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
@Disabled("Этот класс тестов не работает на данный момент")
class SuspiciousCardTransferServiceTest extends ParentTest {

    private static SuspiciousCardTransferEntity suspiciousCardTransfer;
    private static SuspiciousCardTransferEntity updatedSuspiciousCardTransfer;

    private static SuspiciousCardTransferDto updateSuspiciousCardTransferDto;

    @InjectMocks
    private SuspiciousCardTransferServiceImpl service;

    @Mock
    private SuspiciousCardTransferRepository repository;

    @Spy
    private SuspiciousCardTransferMapperImpl mapper;

    @Spy
    private ValidatorSize validatorSize;

    @BeforeAll
    static void init() {
        var suspiciousCardTransferSupplier = new SuspiciousCardTransferSupplier();

        suspiciousCardTransfer = suspiciousCardTransferSupplier.getEntity(ONE, ONE, TRUE, TRUE);

        updateSuspiciousCardTransferDto = suspiciousCardTransferSupplier.getDto(ONE, TWO, FALSE, FALSE);

        updatedSuspiciousCardTransfer = suspiciousCardTransferSupplier.getEntity(ONE, TWO, FALSE, FALSE);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() {
        saveUpdatedMock();

        final SuspiciousCardTransferDto result = service.create(updateSuspiciousCardTransferDto);

        assertAll(
                () -> {
                    assertEquals(updatedSuspiciousCardTransfer.getId(), result.getId());
                    assertEquals(updateSuspiciousCardTransferDto.getIsBlocked(), result.getIsBlocked());
                    assertEquals(updateSuspiciousCardTransferDto.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(updateSuspiciousCardTransferDto.getBlockedReason(), result.getBlockedReason());
                    assertEquals(updateSuspiciousCardTransferDto.getCardTransferId(), result.getCardTransferId());
                    assertEquals(updateSuspiciousCardTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
                }
        );
    }

    @Test()
    @DisplayName("сохранение, негативный сценарий")
    void createNegativeTest() {
        final String messageException = "Entity must not be null.";

        doThrow(new IllegalArgumentException(messageException)).when(repository).save(any());

        final var exception = assertThrows(
                IllegalArgumentException.class, () -> service.create(null)
        );

        assertEquals(messageException, exception.getMessage());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() {
        findByIdMock();

        final SuspiciousCardTransferDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousCardTransfer.getId(), result.getId());
                    assertEquals(suspiciousCardTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousCardTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousCardTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousCardTransfer.getCardTransferId(), result.getCardTransferId());
                    assertEquals(suspiciousCardTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                }
        );
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNegativeTest() {

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_CARD_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("чтение по id равному null, негативный сценарий")
    void readNullNegativeTest() {

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(null)
        );

        assertEquals(getNotFoundExceptionMessage(null, SUSPICIOUS_CARD_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        findByIdMock();
        saveUpdatedMock();

        final SuspiciousCardTransferDto result = service.update(updateSuspiciousCardTransferDto, ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousCardTransfer.getId(), result.getId());
                    assertEquals(updateSuspiciousCardTransferDto.getIsBlocked(), result.getIsBlocked());
                    assertEquals(updateSuspiciousCardTransferDto.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(updateSuspiciousCardTransferDto.getBlockedReason(), result.getBlockedReason());
                    assertEquals(updateSuspiciousCardTransferDto.getCardTransferId(), result.getCardTransferId());
                    assertEquals(updateSuspiciousCardTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан dto null, негативный сценарий")
    void updateNullDtoNegativeTest() {
        findByIdMock();
        repositorySaveMock();

        final SuspiciousCardTransferDto result = service.update(null, ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousCardTransfer.getId(), result.getId());
                    assertEquals(suspiciousCardTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousCardTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousCardTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousCardTransfer.getCardTransferId(), result.getCardTransferId());
                    assertEquals(suspiciousCardTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан id null, позитивный сценарий")
    void updateNullIdTestPositiveTest() {
        findByNullMock();
        repositorySaveMock();

        final SuspiciousCardTransferDto result = service.update(updateSuspiciousCardTransferDto, null);

        assertAll(
                () -> {
                    assertEquals(suspiciousCardTransfer.getId(), result.getId());
                    assertEquals(suspiciousCardTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousCardTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousCardTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousCardTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                    assertEquals(suspiciousCardTransfer.getCardTransferId(), result.getCardTransferId());
                }
        );
    }

    @Test
    @DisplayName("обновление, негативный сценарий")
    void updateNegativeTest() {
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(updateSuspiciousCardTransferDto, ONE)
        );

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_CARD_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<SuspiciousCardTransferDto> suspiciousCardTransfers = readAllTestPrepare();
        final var indexOneTransfer = suspiciousCardTransfers.get(1);
        final var indexZeroTransfer = suspiciousCardTransfers.get(0);

        final String suspiciousReason = indexOneTransfer.getSuspiciousReason();
        final CardTransferDto cardTransferId = indexOneTransfer.getCardTransferId();

        assertAll(
                () -> {
                    assertEquals(TWO, suspiciousCardTransfers.size());
                    assertEquals(suspiciousCardTransfer.getId(), indexZeroTransfer.getId());
                    assertEquals(suspiciousCardTransfer.getIsBlocked(), indexZeroTransfer.getIsBlocked());
                    assertEquals(suspiciousCardTransfer.getIsSuspicious(), indexZeroTransfer.getIsSuspicious());
                    assertEquals(suspiciousCardTransfer.getBlockedReason(), indexZeroTransfer.getBlockedReason());
                    assertEquals(suspiciousCardTransfer.getCardTransferId(), indexZeroTransfer.getCardTransferId());
                    assertEquals(suspiciousCardTransfer.getSuspiciousReason(), indexZeroTransfer.getSuspiciousReason());
                    assertEquals(updatedSuspiciousCardTransfer.getId(), indexOneTransfer.getId());
                    assertEquals(updatedSuspiciousCardTransfer.getCardTransferId(), cardTransferId);
                    assertEquals(updatedSuspiciousCardTransfer.getSuspiciousReason(), suspiciousReason);
                    assertEquals(updatedSuspiciousCardTransfer.getIsBlocked(), indexOneTransfer.getIsBlocked());
                    assertEquals(updatedSuspiciousCardTransfer.getIsSuspicious(), indexOneTransfer.getIsSuspicious());
                    assertEquals(updatedSuspiciousCardTransfer.getBlockedReason(), indexOneTransfer.getBlockedReason());
                }
        );
    }

    private List<SuspiciousCardTransferDto> readAllTestPrepare() {
        doReturn(List.of(suspiciousCardTransfer, updatedSuspiciousCardTransfer)).when(repository).findAllById(any());

        return service.readAll(
                List.of(ONE, TWO)
        );
    }

    @Test
    @DisplayName("чтение по списку несуществующих id, негативный сценарий")
    void readAllNoIdNegativeTest() {
        doReturn(List.of(suspiciousCardTransfer)).when(repository).findAllById(any());

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Количество найденных и запрошенных записей не совпадает.", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("чтение по списку id, передан лист с null-значениями, негативный сценарий")
    void readAllElementNullNegativeTest() {

        final var exception = assertThrows(NullPointerException.class,
                () -> service.readAll(List.of(null, null))
        );

        assertNull(exception.getMessage());
    }

    private void repositorySaveMock() {
        doReturn(suspiciousCardTransfer).when(repository).save(any());
    }

    private void saveUpdatedMock() {
        doReturn(updatedSuspiciousCardTransfer).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(suspiciousCardTransfer)).when(repository).findById(ONE);
    }

    @SuppressWarnings("all")
    private void findByNullMock() {
        doReturn(Optional.of(suspiciousCardTransfer)).when(repository).findById(null);
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }

    @SuppressWarnings("all")
    private void findByNullEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(null);
    }
}
