package com.bank.antifraud.service;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.transferDto.AccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.feign.TransferAccountClient;
import com.bank.antifraud.mapper.SuspiciousAccountTransferMapperImpl;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.impl.SuspiciousAccountTransferServiceImpl;
import com.bank.antifraud.supplier.SuspiciousAccountTransferSupplier;
//import com.bank.antifraud.util.ListSizeValidator;
import com.bank.antifraud.validator.ValidatorSize;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;

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
import static org.mockito.Mockito.*;

public class SuspiciousAccountTransferServiceTest extends ParentTest {

    private static SuspiciousAccountTransferEntity suspiciousAccountTransfer;
    private static SuspiciousAccountTransferEntity updatedSuspiciousAccountTransfer;
    private static SuspiciousAccountTransferDto updateSuspiciousAccountTransferDto;

    private static AccountTransferDto accountTransferDto;

    @InjectMocks
    private SuspiciousAccountTransferServiceImpl service;

    @Mock
    private SuspiciousAccountTransferRepository repository;

    @Spy
    private SuspiciousAccountTransferMapperImpl mapper;

    @Spy
    private ValidatorSize validatorSize;

    @Mock
    private TransferAccountClient transferClient;



    @BeforeAll
    static void init() {
        var suspiciousAccountTransferSupplier = new SuspiciousAccountTransferSupplier();

        suspiciousAccountTransfer = suspiciousAccountTransferSupplier.getEntity(ONE, ONE, TRUE, REASON);

        updatedSuspiciousAccountTransfer = suspiciousAccountTransferSupplier.getEntity(ONE, TWO, FALSE, REASON);

        updateSuspiciousAccountTransferDto = suspiciousAccountTransferSupplier.getDto(ONE, TWO, FALSE, REASON);

        accountTransferDto = new AccountTransferDto(TWO, null, null, null, null);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() {
        saveUpdatedMock();

        final SuspiciousAccountTransferDto result = service.create(updateSuspiciousAccountTransferDto);

        assertAll(
                () -> {
                    assertEquals(updatedSuspiciousAccountTransfer.getId(), result.getId());
                    assertEquals(updateSuspiciousAccountTransferDto.getIsBlocked(), result.getIsBlocked());
                    assertEquals(updateSuspiciousAccountTransferDto.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(updateSuspiciousAccountTransferDto.getBlockedReason(), result.getBlockedReason());
                    assertEquals(updateSuspiciousAccountTransferDto.getSuspiciousReason(),
                            result.getSuspiciousReason()
                    );
                    assertEquals(updateSuspiciousAccountTransferDto.getAccountTransferId(),
                            result.getAccountTransferId()
                    );
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
        when(transferClient.read(any())).thenReturn(ResponseEntity.ok(accountTransferDto));
        final SuspiciousAccountTransferDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousAccountTransfer.getId(), result.getId());
                    assertEquals(suspiciousAccountTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousAccountTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousAccountTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                    assertEquals(suspiciousAccountTransfer.getAccountTransferId(), result.getAccountTransferId());
                }
        );
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNoIdNegativeTest() {

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_ACCOUNT_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("чтение по id равному null, негативный сценарий")
    void readNullNegativeTest() {

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(null)
        );

        assertEquals(getNotFoundExceptionMessage(null, SUSPICIOUS_ACCOUNT_TRANSFER_NAME), exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        findByIdMock();
        saveUpdatedMock();

        final SuspiciousAccountTransferDto result = service.update(updateSuspiciousAccountTransferDto, ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousAccountTransfer.getId(), result.getId());
                    assertEquals(updateSuspiciousAccountTransferDto.getIsBlocked(), result.getIsBlocked());
                    assertEquals(updateSuspiciousAccountTransferDto.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(updateSuspiciousAccountTransferDto.getBlockedReason(), result.getBlockedReason());
                    assertEquals(updateSuspiciousAccountTransferDto.getSuspiciousReason(), result.getSuspiciousReason());
                    assertEquals(updateSuspiciousAccountTransferDto.getAccountTransferId(), result.getAccountTransferId());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан dto null, негативный сценарий")
    void updateNullDtoNegativeTest() {
        saveMock();
        findByIdMock();

        final SuspiciousAccountTransferDto result = service.update(null, ONE);

        assertAll(
                () -> {
                    assertEquals(suspiciousAccountTransfer.getId(), result.getId());
                    assertEquals(suspiciousAccountTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousAccountTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousAccountTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                    assertEquals(suspiciousAccountTransfer.getAccountTransferId(), result.getAccountTransferId());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан id null, негативный сценарий")
    void updateNullIdNegativeTest() {
        saveMock();
        findByNullMock();

        final SuspiciousAccountTransferDto result = service.update(updateSuspiciousAccountTransferDto, null);

        assertAll(
                () -> {
                    assertEquals(suspiciousAccountTransfer.getId(), result.getId());
                    assertEquals(suspiciousAccountTransfer.getIsBlocked(), result.getIsBlocked());
                    assertEquals(suspiciousAccountTransfer.getIsSuspicious(), result.getIsSuspicious());
                    assertEquals(suspiciousAccountTransfer.getBlockedReason(), result.getBlockedReason());
                    assertEquals(suspiciousAccountTransfer.getSuspiciousReason(), result.getSuspiciousReason());
                    assertEquals(suspiciousAccountTransfer.getAccountTransferId(), result.getAccountTransferId());
                }
        );
    }

    @Test
    @DisplayName("обновление по entity равному null, негативный сценарий")
    void updateNegativeTest() {

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(updateSuspiciousAccountTransferDto, ONE)
        );

        assertEquals(getNotFoundExceptionMessage(ONE, SUSPICIOUS_ACCOUNT_TRANSFER_NAME), exception.getMessage());
    }


    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<SuspiciousAccountTransferDto> suspiciousAccountTransfers = readAllTestPrepare();
        final var indexOneTransfer = suspiciousAccountTransfers.get(1);
        final var indexZeroTransfer = suspiciousAccountTransfers.get(0);

        final String suspiciousReason = indexOneTransfer.getSuspiciousReason();
        final Long accountTransferId = indexOneTransfer.getAccountTransferId();

        assertAll(
                () -> {
                    assertEquals(TWO, suspiciousAccountTransfers.size());
                    assertEquals(suspiciousAccountTransfer.getId(), indexZeroTransfer.getId());
                    assertEquals(suspiciousAccountTransfer.getIsBlocked(), indexZeroTransfer.getIsBlocked());
                    assertEquals(suspiciousAccountTransfer.getIsSuspicious(), indexZeroTransfer.getIsSuspicious());
                    assertEquals(suspiciousAccountTransfer.getBlockedReason(), indexZeroTransfer.getBlockedReason());
                    assertEquals(updatedSuspiciousAccountTransfer.getId(), indexOneTransfer.getId());
                    assertEquals(updatedSuspiciousAccountTransfer.getSuspiciousReason(), suspiciousReason);
                    assertEquals(updatedSuspiciousAccountTransfer.getAccountTransferId(), accountTransferId);
                    assertEquals(updatedSuspiciousAccountTransfer.getIsBlocked(), indexOneTransfer.getIsBlocked());
                    assertEquals(updatedSuspiciousAccountTransfer.getIsSuspicious(),
                            indexOneTransfer.getIsSuspicious()
                    );
                    assertEquals(updatedSuspiciousAccountTransfer.getBlockedReason(),
                            indexOneTransfer.getBlockedReason()
                    );
                    assertEquals(suspiciousAccountTransfer.getSuspiciousReason(),
                            indexZeroTransfer.getSuspiciousReason()
                    );
                    assertEquals(suspiciousAccountTransfer.getAccountTransferId(),
                            indexZeroTransfer.getAccountTransferId()
                    );
                }
        );
    }

    private List<SuspiciousAccountTransferDto> readAllTestPrepare() {
        doReturn(List.of(suspiciousAccountTransfer, updatedSuspiciousAccountTransfer)).when(repository).findAllById(
                any()
        );


        return service.readAll(
                List.of(ONE, TWO)
        );
    }

    @Test
    @DisplayName("чтение по списку несуществующих id, негативный сценарий")
    void readAllNoIdNegativeTest() {
        doReturn(List.of(suspiciousAccountTransfer)).when(repository).findAllById(any());

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
        doReturn(suspiciousAccountTransfer).when(repository).save(any());
    }

    private void saveUpdatedMock() {
        doReturn(updatedSuspiciousAccountTransfer).when(repository).save(any());
    }


    private void findByIdMock() {
        doReturn(Optional.of(suspiciousAccountTransfer)).when(repository).findById(ONE);
    }

    @SuppressWarnings("all")
    private void findByNullMock() {
        doReturn(Optional.of(suspiciousAccountTransfer)).when(repository).findById(null);
    }

    @SuppressWarnings("all")
    private void findByNullEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(null);
    }
}
