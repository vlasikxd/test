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
import java.util.ArrayList;
import java.util.Arrays;
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
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final AccountDetailsIdDto result = service.save(updatedAccountDetailsDto);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(updatedAccountDetailsDto.getId(), result.getId());
                    assertEquals(updatedAccountDetailsDto.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("сохранение, негативный сценарий")
    void saveNegativeTest() {
        final String exceptionMessage = "Entity must not be null";
        doThrow(new IllegalArgumentException(exceptionMessage)).when(repository).save(any());

        final var exception = assertThrows(
                IllegalArgumentException.class, () -> service.save(null)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() {
        findByIdMock();

        final AccountDetailsIdDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(accountDetails.getId(), result.getId());
                    assertEquals(accountDetails.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNotExistTdNegativeTest() {
        final String exceptionMessage = "accountDetailsId с данным id не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение, id равен null, негативный сценарий")
    void readIdNullNegativeTest() {
        final String exceptionMessage = "accountDetailsId с данным id не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(null)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        saveMock();
        findByIdMock();

        final AccountDetailsIdDto result = service.update(ONE, updatedAccountDetailsDto);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(accountDetails.getId(), result.getId());
                    assertEquals(updatedAccountDetailsDto.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("обновление, id равен null, негативный сценарий")
    void updateNullIdNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, accountDetailsId не найден!";
        findByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(null, updatedAccountDetailsDto)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("обновление, dto равен null, позитивный сценарий")
    void updateNullDtoPositiveTest() {
        doReturn(accountDetails).when(repository).save(any());
        findByIdMock();

        final AccountDetailsIdDto result = service.update(ONE, null);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(accountDetails.getId(), result.getId());
                    assertEquals(accountDetails.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("обновление несуществующих данных, негативный сценарий")
    void updateNoAccountDetailsIdNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, accountDetailsId не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new AccountDetailsIdDto())
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<AccountDetailsIdDto> accountDetailsIds = readAllTestPrepare();
        final var oneAccountDetails = accountDetailsIds.get(1);
        final var zeroAccountDetails = accountDetailsIds.get(0);

        assertAll(
                () -> {
                    assertNull(zeroAccountDetails.getProfile());
                    assertNull(oneAccountDetails.getProfile());
                    assertEquals(TWO, accountDetailsIds.size());
                    assertEquals(accountDetails.getId(), zeroAccountDetails.getId());
                    assertEquals(updatedAccountDetails.getId(), oneAccountDetails.getId());
                    assertEquals(accountDetails.getAccountId(), zeroAccountDetails.getAccountId());
                    assertEquals(updatedAccountDetails.getAccountId(), oneAccountDetails.getAccountId());
                }
        );
    }

    private List<AccountDetailsIdDto> readAllTestPrepare() {
        doReturn(List.of(accountDetails, updatedAccountDetails)).when(repository).findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id, id равен null, негативный сценарий")
    void readAllIdNullNegativeTest() {
        final String exceptionMessage = "Ошибка в переданных параметрах, accountDetailsId не существуют(ет)";
        doReturn(List.of(new AccountDetailsIdEntity())).when(repository).findAllById(any());

        final List<Long> ids = new ArrayList<>(Arrays.asList(null, ONE));

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(ids)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNotExistIdsNegativeTest() {
        final String exceptionMessage = "Ошибка в переданных параметрах, accountDetailsId не существуют(ет)";

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    private void saveMock() {
        doReturn(updatedAccountDetails).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(accountDetails)).when(repository).findById(any());
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(any());
    }
}
