package com.bank.history.service;

import com.bank.history.ParentTest;
import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapperImpl;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest extends ParentTest {

    private static HistoryEntity historyEntity;

    private static List<HistoryEntity> histories;

    @InjectMocks
    private HistoryServiceImpl service;

    @Mock
    private HistoryRepository repository;

    @Spy
    private HistoryMapperImpl mapper;

    @BeforeAll
    static void init() {
        historyEntity = getHistoryEntity(ONE, ONE, ONE, ONE, TWO, THREE, ONE);

        histories = getHistories(historyEntity);
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() {
        doReturn(Optional.of(historyEntity)).when(repository).findById(anyLong());

        final HistoryDto result = service.readById(ONE);

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
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readById(ONE)
        );

        assertEquals("история по указанному id не найдена", exception.getMessage());
    }

    @Test
    @DisplayName("чтение списка позитивный сценарий")
    void readAllTest() {
        doReturn(histories).when(repository).findAllById(any());

        final List<HistoryDto> result = service.readAllById(List.of(ONE));

        assertAll( () -> {
            assertEquals(histories.size(), result.size());
            assertEquals(getZeroEntityElement(histories).getId(), getZeroElement(result).getId());
            assertEquals(getZeroEntityElement(histories).getTransferAuditId(), getZeroElement(result).getTransferAuditId());

            assertEquals(getZeroEntityElement(histories).getAntiFraudAuditId(),
                    getZeroElement(result).getAntiFraudAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getAccountAuditId(),
                    getZeroElement(result).getAccountAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getProfileAuditId(),
                    getZeroElement(result).getProfileAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getPublicBankInfoAuditId(),
                    getZeroElement(result).getPublicBankInfoAuditId()
            );
            assertEquals(getZeroEntityElement(histories).getAuthorizationAuditId(),
                    getZeroElement(result).getAuthorizationAuditId()
            );
        });
    }

    @Test
    @DisplayName("чтение списка негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new HistoryEntity())).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readAllById(List.of(ONE, TWO))
        );

        assertEquals("истории по указанным id не найдены", exception.getMessage());
    }

    @Test
    @DisplayName("создание позитивный сценарий")
    void createTest() {
        doReturn(historyEntity).when(repository).save(any());

        final HistoryDto result = service.create(
                getHistoryDto(ONE, ONE, ONE, ONE, TWO, THREE, ONE)
        );

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
    @DisplayName("создание негативный сценарий")
    void createNegativeTest() {
        String massage = "Недопустимые параметры";

        doThrow(new IllegalArgumentException(massage)).when(repository).save(any());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.create(new HistoryDto())
        );

        assertEquals(massage, exception.getMessage());
    }



    @Test()
    @DisplayName("обновление с не пустым id и null")
    void updateNullTest() {
        doReturn(Optional.of(historyEntity)).when(repository).findById(ONE);
        doReturn(historyEntity).when(repository).save(any());

        final HistoryDto result = service.update(ONE, null);

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
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(ONE, new HistoryDto()));

        assertEquals("указанная история не найдена " + ONE, exception.getMessage());
    }
}
