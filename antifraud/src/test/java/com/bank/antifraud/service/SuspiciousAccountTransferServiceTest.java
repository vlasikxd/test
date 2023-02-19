package com.bank.antifraud.service;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.mapper.SuspiciousAccountTransferMapperImpl;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.impl.SuspiciousAccountTransferServiceImpl;
import com.bank.antifraud.supplier.SuspiciousAccountTransferSupplier;
import com.bank.antifraud.util.ListSizeValidator;
import org.junit.jupiter.api.BeforeAll;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class SuspiciousAccountTransferServiceTest extends ParentTest {

    private static SuspiciousAccountTransferEntity suspiciousAccountTransfer;
    private static SuspiciousAccountTransferEntity updatedSuspiciousAccountTransfer;

    private static SuspiciousAccountTransferDto updateSuspiciousAccountTransferDto;

    @InjectMocks
    private SuspiciousAccountTransferServiceImpl service;

    @Mock
    private SuspiciousAccountTransferRepository repository;

    @Spy
    private SuspiciousAccountTransferMapperImpl mapper;

    @Spy
    private ListSizeValidator listSizeValidator;

    @BeforeAll
    static void init() {
        var suspiciousAccountTransferSupplier = new SuspiciousAccountTransferSupplier();

        suspiciousAccountTransfer = suspiciousAccountTransferSupplier.getEntity(ONE, ONE, TRUE, TRUE, REASON, REASON);

        updatedSuspiciousAccountTransfer = suspiciousAccountTransferSupplier.getEntity(
                ONE, TWO, FALSE, FALSE, REASON, REASON
        );

        updateSuspiciousAccountTransferDto = suspiciousAccountTransferSupplier.getDto(
                ONE, TWO, FALSE, FALSE, REASON, REASON
        );
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void createTest() {
        repositorySaveMock();

        final SuspiciousAccountTransferDto result = service.create(updateSuspiciousAccountTransferDto);

        assertAll(() -> {
            assertEquals(updatedSuspiciousAccountTransfer.getId(), result.getId());
            assertEquals(updateSuspiciousAccountTransferDto.getIsBlocked(), result.getIsBlocked());
            assertEquals(updateSuspiciousAccountTransferDto.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(updateSuspiciousAccountTransferDto.getBlockedReason(), result.getBlockedReason());
            assertEquals(updateSuspiciousAccountTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
            assertEquals(updateSuspiciousAccountTransferDto.getAccountTransferId(), result.getAccountTransferId());
        });
    }

    @Test()
    @DisplayName("сохранение негативный сценарий")
    void createNegativeTest() {
        doThrow(new IllegalArgumentException("Entity must not be null.")).when(repository).save(any());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.create(null)
        );

        assertEquals("Entity must not be null.", exception.getMessage());
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() {
        repositoryFindByIdMock();

        final SuspiciousAccountTransferDto result = service.read(ONE);

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
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.read(ONE));

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_ACCOUNT_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() {
        repositorySaveMock();
        repositoryFindByIdMock();

        final SuspiciousAccountTransferDto result = service.update(updateSuspiciousAccountTransferDto, ONE);

        assertAll(() -> {
            assertEquals(suspiciousAccountTransfer.getId(), result.getId());
            assertEquals(updateSuspiciousAccountTransferDto.getIsBlocked(), result.getIsBlocked());
            assertEquals(updateSuspiciousAccountTransferDto.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(updateSuspiciousAccountTransferDto.getBlockedReason(), result.getBlockedReason());
            assertEquals(updateSuspiciousAccountTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
            assertEquals(updateSuspiciousAccountTransferDto.getAccountTransferId(), result.getAccountTransferId());
        });
    }

    @Test
    @DisplayName("обновление, где dto равен null")
    void updateNullDtoTest() {
        doReturn(suspiciousAccountTransfer).when(repository).save(any());
        repositoryFindByIdMock();

        final SuspiciousAccountTransferDto result = service.update(null, ONE);

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
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(updateSuspiciousAccountTransferDto, ONE)
        );

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_ACCOUNT_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id позитивный сценарий")
    void readAllTest() {
        final List<SuspiciousAccountTransferDto> suspiciousAccountTransfers = readAllTestPrepare();
        final var indexOneTransfer = suspiciousAccountTransfers.get(1);
        final var indexZeroTransfer = suspiciousAccountTransfers.get(0);

        final String suspiciousReason = indexOneTransfer.getSuspiciousReason();
        final Long accountTransferId = indexOneTransfer.getAccountTransferId();

        assertAll(() -> {
            assertEquals(TWO, suspiciousAccountTransfers.size());
            assertEquals(suspiciousAccountTransfer.getId(), indexZeroTransfer.getId());
            assertEquals(suspiciousAccountTransfer.getIsBlocked(), indexZeroTransfer.getIsBlocked());
            assertEquals(suspiciousAccountTransfer.getIsSuspicious(), indexZeroTransfer.getIsSuspicious());
            assertEquals(suspiciousAccountTransfer.getBlockedReason(), indexZeroTransfer.getBlockedReason());
            assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), indexZeroTransfer.getSuspiciousReason());
            assertEquals(suspiciousAccountTransfer.getAccountTransferId(), indexZeroTransfer.getAccountTransferId());

            assertEquals(updatedSuspiciousAccountTransfer.getId(), indexOneTransfer.getId());
            assertEquals(updatedSuspiciousAccountTransfer.getSuspiciousReason(), suspiciousReason);
            assertEquals(updatedSuspiciousAccountTransfer.getAccountTransferId(), accountTransferId);
            assertEquals(updatedSuspiciousAccountTransfer.getIsBlocked(), indexOneTransfer.getIsBlocked());
            assertEquals(updatedSuspiciousAccountTransfer.getIsSuspicious(), indexOneTransfer.getIsSuspicious());
            assertEquals(updatedSuspiciousAccountTransfer.getBlockedReason(), indexOneTransfer.getBlockedReason());
        });
    }

    private List<SuspiciousAccountTransferDto> readAllTestPrepare() {
        doReturn(List.of(suspiciousAccountTransfer, updatedSuspiciousAccountTransfer))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(suspiciousAccountTransfer)).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Количество найденных и запрошенных записей не совпадает.", exception.getMessage());
    }

    private void repositorySaveMock() {
        doReturn(updatedSuspiciousAccountTransfer).when(repository).save(any());
    }

    private void repositoryFindByIdMock() {
        doReturn(Optional.of(suspiciousAccountTransfer)).when(repository).findById(ONE);
    }

    private void repositoryFindByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
