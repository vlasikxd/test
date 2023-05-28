package com.bank.antifraud.service;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.dto.transferDto.PhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.mapper.SuspiciousPhoneTransferMapperImpl;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.impl.SuspiciousPhoneTransferServiceImpl;
import com.bank.antifraud.supplier.SuspiciousPhoneTransferSupplier;
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
class SuspiciousPhoneTransferServiceTest extends ParentTest {

    private static SuspiciousPhoneTransferEntity suspiciousPhoneTransfer;
    private static SuspiciousPhoneTransferEntity updatedSuspiciousPhoneTransfer;

    private static SuspiciousPhoneTransferDto updateSuspiciousPhoneTransferDto;

    @InjectMocks
    private SuspiciousPhoneTransferServiceImpl service;

    @Mock
    private SuspiciousPhoneTransferRepository repository;

    @Spy
    private static SuspiciousPhoneTransferMapperImpl mapper;

    @Spy
    private ValidatorSize validatorSize;

    @BeforeAll
    static void init() {

        var suspiciousPhoneTransferSupplier = new SuspiciousPhoneTransferSupplier();

        suspiciousPhoneTransfer = suspiciousPhoneTransferSupplier.getEntity(ONE, ONE, TRUE, TRUE);

        updatedSuspiciousPhoneTransfer = suspiciousPhoneTransferSupplier.getEntity(ONE, TWO, FALSE, FALSE);

        updateSuspiciousPhoneTransferDto = suspiciousPhoneTransferSupplier.getDto(ONE, TWO, FALSE, FALSE);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() {
        saveUpdatedMock();

        final SuspiciousPhoneTransferDto result = service.create(updateSuspiciousPhoneTransferDto);

        assertAll(
                () -> {
                    assertEquals(updatedSuspiciousPhoneTransfer.getId(), result.getId());
                    assertEquals(updateSuspiciousPhoneTransferDto.getIsBlocked(), result.getIsBlocked());
                    assertEquals(updateSuspiciousPhoneTransferDto.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(updateSuspiciousPhoneTransferDto.getBlockedReason(), result.getBlockedReason());
                    assertEquals(updateSuspiciousPhoneTransferDto.getPhoneTransferId(), result.getPhoneTransferId());
                    assertEquals(updateSuspiciousPhoneTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
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

        final SuspiciousPhoneTransferDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
                    assertEquals(suspiciousPhoneTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousPhoneTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), result.getPhoneTransferId());
                    assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                }
        );
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNoIdNegativeTest() {

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_PHONE_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("чтение по id равному null, негативный сценарий")
    void readNullNegativeTest() {

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(null)
        );

        assertEquals(getNotFoundExceptionMessage(null, SUSPICIOUS_PHONE_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        findByIdMock();
        saveUpdatedMock();

        final SuspiciousPhoneTransferDto result = service.update(updateSuspiciousPhoneTransferDto, ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
                    assertEquals(updateSuspiciousPhoneTransferDto.getIsBlocked(), result.getIsBlocked());
                    assertEquals(updateSuspiciousPhoneTransferDto.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(updateSuspiciousPhoneTransferDto.getBlockedReason(), result.getBlockedReason());
                    assertEquals(updateSuspiciousPhoneTransferDto.getPhoneTransferId(), result.getPhoneTransferId());
                    assertEquals(updateSuspiciousPhoneTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан dto null, негативный сценарий")
    void updateNullDtoNegativeTest() {
        saveMock();
        findByIdMock();

        final SuspiciousPhoneTransferDto result = service.update(null, ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
                    assertEquals(suspiciousPhoneTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousPhoneTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), result.getPhoneTransferId());
                    assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан id null, негативный сценарий")
    void updateNullIdNegativeTest() {
        saveMock();
        findByNullMock();

        final SuspiciousPhoneTransferDto result = service.update(updateSuspiciousPhoneTransferDto, null);

        assertAll(
                () -> {
                    assertEquals(suspiciousPhoneTransfer.getId(), result.getId());
                    assertEquals(suspiciousPhoneTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousPhoneTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), result.getPhoneTransferId());
                    assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                }
        );
    }

    @Test
    @DisplayName("обновление по entity равному null, негативный сценарий")
    void updateNegativeTest() {

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(updateSuspiciousPhoneTransferDto, ONE)
        );

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_PHONE_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<SuspiciousPhoneTransferDto> suspiciousPhoneTransfers = readAllTestPrepare();
        final var indexOneTransfer = suspiciousPhoneTransfers.get(1);
        final var indexZeroTransfer = suspiciousPhoneTransfers.get(0);

        final String suspiciousReason = indexOneTransfer.getSuspiciousReason();
        final PhoneTransferDto phoneTransferId = indexOneTransfer.getPhoneTransferId();

        assertAll(
                () -> {
                    assertEquals(TWO, suspiciousPhoneTransfers.size());
                    assertEquals(suspiciousPhoneTransfer.getId(), indexZeroTransfer.getId());
                    assertEquals(suspiciousPhoneTransfer.getIsBlocked(), indexZeroTransfer.getIsBlocked());
                    assertEquals(suspiciousPhoneTransfer.getIsSuspicious(), indexZeroTransfer.getIsSuspicious());
                    assertEquals(suspiciousPhoneTransfer.getBlockedReason(), indexZeroTransfer.getBlockedReason());
                    assertEquals(suspiciousPhoneTransfer.getPhoneTransferId(), indexZeroTransfer.getPhoneTransferId());
                    assertEquals(updatedSuspiciousPhoneTransfer.getId(), indexOneTransfer.getId());
                    assertEquals(updatedSuspiciousPhoneTransfer.getPhoneTransferId(), phoneTransferId);
                    assertEquals(updatedSuspiciousPhoneTransfer.getSuspiciousReason(), suspiciousReason);
                    assertEquals(updatedSuspiciousPhoneTransfer.getIsBlocked(), indexOneTransfer.getIsBlocked());
                    assertEquals(updatedSuspiciousPhoneTransfer.getIsSuspicious(), indexOneTransfer.getIsSuspicious());
                    assertEquals(suspiciousPhoneTransfer.getSuspiciousReason(),
                            indexZeroTransfer.getSuspiciousReason()
                    );
                    assertEquals(updatedSuspiciousPhoneTransfer.getBlockedReason(),
                            indexOneTransfer.getBlockedReason()
                    );
                }
        );
    }

    private List<SuspiciousPhoneTransferDto> readAllTestPrepare() {
        doReturn(List.of(suspiciousPhoneTransfer, updatedSuspiciousPhoneTransfer)).when(repository).findAllById(any());

        return service.readAll(
                List.of(ONE, TWO)
        );
    }

    @Test
    @DisplayName("чтение по списку несуществующих id, негативный сценарий")
    void readAllNoIdNegativeTest() {
        doReturn(List.of(suspiciousPhoneTransfer)).when(repository).findAllById(any());

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Количество найденных и запрошенных записей не совпадает.", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("чтение по списку id, передан лист с null-значениями, негативный сценарий")
    void readAllElementNullNegativeTest() {

        final var exception = assertThrows(
                NullPointerException.class, () -> service.readAll(List.of(null, null))
        );

        assertNull(exception.getMessage());
    }

    private void saveMock() {
        doReturn(suspiciousPhoneTransfer).when(repository).save(any());
    }

    private void saveUpdatedMock() {
        doReturn(updatedSuspiciousPhoneTransfer).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(suspiciousPhoneTransfer)).when(repository).findById(ONE);
    }

    @SuppressWarnings("all")
    private void findByNullMock() {
        doReturn(Optional.of(suspiciousPhoneTransfer)).when(repository).findById(null);
    }

    @SuppressWarnings("all")
    private void findByNullEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(null);
    }
}
