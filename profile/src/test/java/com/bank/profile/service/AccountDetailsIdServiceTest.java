package com.bank.profile.service;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapperImpl;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.imp.AccountDetailsIdServiceImp;
import com.bank.profile.supplier.AccountDetailsIdSupplier;
import com.bank.profile.validator.EntityListValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class AccountDetailsIdServiceTest extends ParentTest {

    private static AccountDetailsIdEntity accountDetails;
    private static AccountDetailsIdEntity updatedAccountDetails;
    private static AccountDetailsIdDto updatedAccountDetailsDto;

    @Mock
    private AccountDetailsIdRepository repository;

    @InjectMocks
    private AccountDetailsIdServiceImp service;

    @Spy
    private AccountDetailsIdMapperImpl mapper;
    @Spy
    private EntityListValidator validator;

    @BeforeAll
    static void init() {
        AccountDetailsIdSupplier accountDetailsIdSupplier = new AccountDetailsIdSupplier();

        accountDetails = accountDetailsIdSupplier.getEntity(ONE, TWO, null);
        updatedAccountDetails = accountDetailsIdSupplier.getEntity(ONE, ONE, null);

        updatedAccountDetailsDto = accountDetailsIdSupplier.getDto(ONE, ONE, null);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() {
        repositorySaveMock();

        final AccountDetailsIdDto result = service.save(updatedAccountDetailsDto);

        assertAll(() -> {
            assertNull(result.getProfile());
            assertEquals(updatedAccountDetailsDto.getId(), result.getId());
            assertEquals(updatedAccountDetailsDto.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("сохранение негативный сценарий")
    void saveNegativeTest() {
        doThrow(new IllegalArgumentException("Недопустимые параметры")).when(repository).save(any());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.save(null)
        );

        assertEquals("Недопустимые параметры", exception.getMessage());
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() {
        repositoryFindByIdMock();

        final AccountDetailsIdDto result = service.read(ONE);

        assertAll(() -> {
            assertNull(result.getProfile());
            assertEquals(accountDetails.getId(), result.getId());
            assertEquals(accountDetails.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.read(ONE));

        assertEquals("accountDetailsId с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() {
        repositorySaveMock();
        repositoryFindByIdMock();

        final AccountDetailsIdDto result = service.update(ONE, updatedAccountDetailsDto);

        assertAll(() -> {
            assertNull(result.getProfile());
            assertEquals(accountDetails.getId(), result.getId());
            assertEquals(updatedAccountDetailsDto.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("обновление, где dto равен null")
    void updateNullTest() {
        doReturn(accountDetails).when(repository).save(any());
        repositoryFindByIdMock();

        final AccountDetailsIdDto result = service.update(ONE, null);

        assertAll(() -> {
            assertNull(result.getProfile());
            assertEquals(accountDetails.getId(), result.getId());
            assertEquals(accountDetails.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(ONE, new AccountDetailsIdDto())
        );

        assertEquals("Обновление невозможно, accountDetailsId не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id позитивный сценарий")
    void readAllTest() {
        final List<AccountDetailsIdDto> accountDetailsIds = readAllTestPrepare();
        final var zeroAccountDetails = accountDetailsIds.get(0);
        final var oneAccountDetails = accountDetailsIds.get(1);

        assertAll(() -> {
            assertNull(zeroAccountDetails.getProfile());
            assertNull(oneAccountDetails.getProfile());

            assertEquals(TWO, accountDetailsIds.size());
            assertEquals(accountDetails.getId(), zeroAccountDetails.getId());
            assertEquals(updatedAccountDetails.getId(), oneAccountDetails.getId());
            assertEquals(accountDetails.getAccountId(), zeroAccountDetails.getAccountId());
            assertEquals(updatedAccountDetails.getAccountId(), oneAccountDetails.getAccountId());
        });
    }

    private List<AccountDetailsIdDto> readAllTestPrepare() {
        doReturn(List.of(accountDetails, updatedAccountDetails))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new AccountDetailsIdEntity())).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, accountDetailsId не существуют(ет)",
                exception.getMessage());
    }

    private void repositorySaveMock() {
        doReturn(updatedAccountDetails).when(repository).save(any());
    }

    private void repositoryFindByIdMock() {
        doReturn(Optional.of(accountDetails)).when(repository).findById(ONE);
    }

    private void repositoryFindByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
