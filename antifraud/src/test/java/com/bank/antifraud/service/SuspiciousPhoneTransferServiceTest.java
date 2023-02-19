package com.bank.antifraud.service;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.mapper.SuspiciousPhoneTransferMapperImpl;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.impl.SuspiciousPhoneTransferServiceImpl;
import com.bank.antifraud.supplier.SuspiciousPhoneTransferSupplier;
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

class SuspiciousPhoneTransferServiceTest extends ParentTest {

    private static SuspiciousPhoneTransferEntity suspiciousPhoneTransfer;
    private static SuspiciousPhoneTransferEntity updatedSuspiciousPhoneTransfer;

    private static SuspiciousPhoneTransferDto updateSuspiciousPhoneTransferDto;

    @InjectMocks
    private SuspiciousPhoneTransferServiceImpl service;

    @Mock
    private SuspiciousPhoneTransferRepository repository;

    @Spy
    private SuspiciousPhoneTransferMapperImpl mapper;

    @Spy
    private ListSizeValidator listSizeValidator;

    @BeforeAll
    static void init() {
        var suspiciousPhoneTransferSupplier = new SuspiciousPhoneTransferSupplier();

        suspiciousPhoneTransfer = suspiciousPhoneTransferSupplier.getEntity(ONE, ONE, TRUE, TRUE, REASON, REASON);

        updatedSuspiciousPhoneTransfer = suspiciousPhoneTransferSupplier.getEntity(
                ONE, TWO, FALSE, FALSE, REASON, REASON
        );

        updateSuspiciousPhoneTransferDto = suspiciousPhoneTransferSupplier.getDto(
                ONE, TWO, FALSE, FALSE, REASON, REASON
        );
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void createTest() {
        repositorySaveMock();

        final SuspiciousPhoneTransferDto result = service.create(updateSuspiciousPhoneTransferDto);

        assertAll(() -> {
            assertEquals(updatedSuspiciousPhoneTransfer.getId(), result.getId());
            assertEquals(updateSuspiciousPhoneTransferDto.getIsBlocked(), result.getIsBlocked());
            assertEquals(updateSuspiciousPhoneTransferDto.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(updateSuspiciousPhoneTransferDto.getBlockedReason(), result.getBlockedReason());
            assertEquals(updateSuspiciousPhoneTransferDto.getPhoneTransferId(), result.getPhoneTransferId());
            assertEquals(updateSuspiciousPhoneTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
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

        final SuspiciousPhoneTransferDto result = service.read(ONE);

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
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.read(ONE));

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_PHONE_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() {
        repositorySaveMock();
        repositoryFindByIdMock();

        final SuspiciousPhoneTransferDto result = service.update(updateSuspiciousPhoneTransferDto, ONE);

        assertAll(() -> {
            assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
            assertEquals(updateSuspiciousPhoneTransferDto.getIsBlocked(), result.getIsBlocked());
            assertEquals(updateSuspiciousPhoneTransferDto.getIsSuspicious(), result.getIsSuspicious());
            assertEquals(updateSuspiciousPhoneTransferDto.getBlockedReason(), result.getBlockedReason());
            assertEquals(updateSuspiciousPhoneTransferDto.getPhoneTransferId(), result.getPhoneTransferId());
            assertEquals(updateSuspiciousPhoneTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("обновление, где dto равен null")
    void updateNullDtoTest() {
        doReturn(suspiciousPhoneTransfer).when(repository).save(any());
        repositoryFindByIdMock();

        final SuspiciousPhoneTransferDto result = service.update(null, ONE);

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
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(updateSuspiciousPhoneTransferDto, ONE)
        );

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_PHONE_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id позитивный сценарий")
    void readAllTest() {
        final List<SuspiciousPhoneTransferDto> suspiciousPhoneTransfers = readAllTestPrepare();
        final var indexOneTransfer = suspiciousPhoneTransfers.get(1);
        final var indexZeroTransfer = suspiciousPhoneTransfers.get(0);

        final String suspiciousReason = indexOneTransfer.getSuspiciousReason();
        final Long phoneTransferId = indexOneTransfer.getPhoneTransferId();

        assertAll(() -> {
            assertEquals(TWO, suspiciousPhoneTransfers.size());
            assertEquals(suspiciousPhoneTransfer.getId(), indexZeroTransfer.getId());
            assertEquals(suspiciousPhoneTransfer.getIsBlocked(), indexZeroTransfer.getIsBlocked());
            assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), indexZeroTransfer.getIsSuspicious());
            assertEquals(suspiciousPhoneTransfer.getBlockedReason(), indexZeroTransfer.getBlockedReason());
            assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), indexZeroTransfer.getPhoneTransferId());
            assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), indexZeroTransfer.getSuspiciousReason());

            assertEquals(updatedSuspiciousPhoneTransfer.getId(), indexOneTransfer.getId());
            assertEquals(updatedSuspiciousPhoneTransfer.getPhoneTransferId(), phoneTransferId);
            assertEquals(updatedSuspiciousPhoneTransfer.getSuspiciousReason(), suspiciousReason);
            assertEquals(updatedSuspiciousPhoneTransfer.getIsBlocked(), indexOneTransfer.getIsBlocked());
            assertEquals(updatedSuspiciousPhoneTransfer.getIsSuspicious(), indexOneTransfer.getIsSuspicious());
            assertEquals(updatedSuspiciousPhoneTransfer.getBlockedReason(), indexOneTransfer.getBlockedReason());
        });
    }

    private List<SuspiciousPhoneTransferDto> readAllTestPrepare() {
        doReturn(List.of(suspiciousPhoneTransfer, updatedSuspiciousPhoneTransfer))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(suspiciousPhoneTransfer)).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Количество найденных и запрошенных записей не совпадает.", exception.getMessage());
    }

    private void repositorySaveMock() {
        doReturn(updatedSuspiciousPhoneTransfer).when(repository).save(any());
    }

    private void repositoryFindByIdMock() {
        doReturn(Optional.of(suspiciousPhoneTransfer)).when(repository).findById(ONE);
    }

    private void repositoryFindByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
