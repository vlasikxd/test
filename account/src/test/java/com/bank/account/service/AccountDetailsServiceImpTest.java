package com.bank.account.service;

import com.bank.account.ParentTest;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapperImpl;
import com.bank.account.repository.AccountDetailsRepository;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
//  TODO  разверни импорты
import static org.mockito.Mockito.*;
import static com.bank.account.supplier.AccountDetailsSupplier.*;

@ExtendWith(MockitoExtension.class)
public class AccountDetailsServiceImpTest extends ParentTest {

    private static AccountDetailsEntity accountDetails;

    private static List<AccountDetailsEntity> accountDetailsList;

    @InjectMocks
    private AccountDetailsServiceImp service;

    @Mock
    private AccountDetailsRepository repository;

    @Spy
    private AccountDetailsMapperImpl mapper;

    @BeforeAll
    static void init() {
        accountDetails = getAccountDetails(ONE, ONE, ONE, ONE, BIG_DECIMAL_THREE, Boolean.TRUE, ONE);

        accountDetailsList = getAccountDetailsList(accountDetails);
    }

    @Test
    // TODO переименуй в "чтение, позитивный сценарий"
    @DisplayName("чтение позитивный сценарий")
    void readTest() {
        doReturn(Optional.of(accountDetails)).when(repository).findById(anyLong());

        final AccountDetailsDto result = service.readById(ONE);

        //        TODO приведи к виду
        //         assertAll(
        //                () -> {
        //                    assertEquals(accountDetails.getId(), result.getId());
        //                    assertEquals(accountDetails.getMoney(), result.getMoney());
        //                    assertEquals(accountDetails.getProfileId(), result.getProfileId());
        //                    assertEquals(accountDetails.getPassportId(), result.getPassportId());
        //                    assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
        //                    assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
        //                    assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        //                }
        //        );
        assertAll(() -> {
            assertEquals(accountDetails.getId(), result.getId());
            assertEquals(accountDetails.getMoney(), result.getMoney());
            assertEquals(accountDetails.getProfileId(), result.getProfileId());
            assertEquals(accountDetails.getPassportId(), result.getPassportId());
            assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
            assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
            assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        });
    }

    @Test
    // TODO переименуй в "чтение, негативный сценарий"
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readById(ONE)
        );

        assertEquals("AccountDetails с таким id не найдено", exception.getMessage());
    }

    @Test
    @DisplayName("чтение id = null, негативный сценарий")
    void readIdIsNullNegativeTest() {
        when(repository.findById(any()))
                .thenThrow(new IllegalArgumentException("The id must not be null!"));

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> repository.findById(ONE)
        );

        assertEquals("The id must not be null!", exception.getMessage());
    }

    @Test
    // TODO переименуй в "чтение списка, позитивный сценарий"
    @DisplayName("чтение списка позитивный сценарий")
    void readAllTest() {
        doReturn(accountDetailsList).when(repository).findAllById(any());

        final List<AccountDetailsDto> result = service.readAllById(List.of(ONE));

        // TODO приведи к виду
        //  assertAll(
        //                () -> {
        //                    assertEquals(accountDetailsList.size(), result.size());
        //                    assertEquals(getZeroElement(accountDetailsList).getId(), getZeroElement(result).getId());
        //                    assertEquals(getZeroElement(accountDetailsList).getMoney(), getZeroElement(result).getMoney());
        //
        //                    assertEquals(getZeroElement(accountDetailsList).getProfileId(),
        //                            getZeroElement(result).getProfileId()
        //                    );
        //                    assertEquals(getZeroElement(accountDetailsList).getPassportId(),
        //                            getZeroElement(result).getPassportId()
        //                    );
        //                    assertEquals(getZeroElement(accountDetailsList).getAccountNumber(),
        //                            getZeroElement(result).getAccountNumber()
        //                    );
        //                    assertEquals(getZeroElement(accountDetailsList).getBankDetailsId(),
        //                            getZeroElement(result).getBankDetailsId()
        //                    );
        //                    assertEquals(getZeroElement(accountDetailsList).getNegativeBalance(),
        //                            getZeroElement(result).getNegativeBalance()
        //                    );
        //                }
        //        );
        assertAll(() -> {
            assertEquals(accountDetailsList.size(), result.size());
            assertEquals(getZeroElement(accountDetailsList).getId(), getZeroElement(result).getId());
            assertEquals(getZeroElement(accountDetailsList).getMoney(), getZeroElement(result).getMoney());

            assertEquals(getZeroElement(accountDetailsList).getProfileId(),
                    getZeroElement(result).getProfileId()
            );
            assertEquals(getZeroElement(accountDetailsList).getPassportId(),
                    getZeroElement(result).getPassportId()
            );
            assertEquals(getZeroElement(accountDetailsList).getAccountNumber(),
                    getZeroElement(result).getAccountNumber()
            );
            assertEquals(getZeroElement(accountDetailsList).getBankDetailsId(),
                    getZeroElement(result).getBankDetailsId()
            );
            assertEquals(getZeroElement(accountDetailsList).getNegativeBalance(),
                    getZeroElement(result).getNegativeBalance()
            );
        });
    }

    @Test
    // TODO переименуй в "чтение списка, негативный сценарий"
    @DisplayName("чтение списка негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new AccountDetailsEntity())).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readAllById(List.of(ONE, TWO))
        );

        assertEquals("Одного или нескольких id из списка не найдено", exception.getMessage());
    }

    @Test
    @DisplayName("чтение списка, ids = null, негативный сценарий")
    void readAllIdIsNullNegativeTest() {
        when(repository.findAllById(anyList()))
                .thenThrow(new IllegalArgumentException("The id must not be null!"));

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.readAllById(List.of(ONE, TWO)));

        assertEquals("The id must not be null!", exception.getMessage());
    }

    @Test
    // TODO переименуй в "создание, позитивный сценарий"
    @DisplayName("создание позитивный сценарий")
    void createTest() {
        doReturn(accountDetails).when(repository).save(any());

        final AccountDetailsDto result = service.create(
                getAccountDetailsDto(ONE, ONE, ONE, ONE, BIG_DECIMAL_THREE, Boolean.TRUE, ONE)
        );

        // TODO приведи к виду
        //  assertAll(
        //                () -> {
        //                    assertEquals(accountDetails.getId(), result.getId());
        //                    assertEquals(accountDetails.getMoney(), result.getMoney());
        //                    assertEquals(accountDetails.getProfileId(), result.getProfileId());
        //                    assertEquals(accountDetails.getPassportId(), result.getPassportId());
        //                    assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
        //                    assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
        //                    assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        //                }
        //        );
        assertAll(() -> {
            assertEquals(accountDetails.getId(), result.getId());
            assertEquals(accountDetails.getMoney(), result.getMoney());
            assertEquals(accountDetails.getProfileId(), result.getProfileId());
            assertEquals(accountDetails.getPassportId(), result.getPassportId());
            assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
            assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
            assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        });
    }

    @Test
    // TODO переименуй в "создание, негативный сценарий"
    @DisplayName("создание негативный сценарий")
    void createNegativeTest() {
        String massage = "Недопустимые параметры";

        doThrow(new IllegalArgumentException(massage)).when(repository).save(any());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.create(new AccountDetailsDto())
        );

        assertEquals(massage, exception.getMessage());
    }

    @Test
    // TODO переименуй в "обновление, позитивный сценарий"
    @DisplayName("обновление позитивный сценарий")
    void updateTest() {
        doReturn(Optional.of(accountDetails)).when(repository).findById(ONE);
        // TODO добавь пустую строку
        doReturn(accountDetails).when(repository).save(
                getAccountDetails(ONE, TWO, TWO, TWO, BIG_DECIMAL_THREE, Boolean.FALSE, TWO)
        );

        final AccountDetailsDto result = service.update(
                ONE, getAccountDetailsDto(null, TWO, TWO, TWO, BIG_DECIMAL_THREE, Boolean.FALSE, TWO)
        );

        // TODO приведи к виду
        //  assertAll(
        //                () -> {
        //                    assertEquals(accountDetails.getId(), result.getId());
        //                    assertEquals(accountDetails.getMoney(), result.getMoney());
        //                    assertEquals(accountDetails.getProfileId(), result.getProfileId());
        //                    assertEquals(accountDetails.getPassportId(), result.getPassportId());
        //                    assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
        //                    assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
        //                    assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        //                }
        //        );
        assertAll(() -> {
            assertEquals(accountDetails.getId(), result.getId());
            assertEquals(accountDetails.getMoney(), result.getMoney());
            assertEquals(accountDetails.getProfileId(), result.getProfileId());
            assertEquals(accountDetails.getPassportId(), result.getPassportId());
            assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
            assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
            assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        });
    }

    // TODO ненужные скобки в аннотации
    @Test()
    // TODO переименуй в  "обновление с не пустым id и null, позитивный сценарий"
    @DisplayName("обновление с не пустым id и null")
    void updateNullTest() {
        doReturn(Optional.of(accountDetails)).when(repository).findById(ONE);
        // TODO добавь пустую строку
        doReturn(accountDetails).when(repository).save(any());

        final AccountDetailsDto result = service.update(ONE, null);

        // TODO приведи к виду
        //  assertAll(
        //                () -> {
        //                    assertEquals(accountDetails.getId(), result.getId());
        //                    assertEquals(accountDetails.getMoney(), result.getMoney());
        //                    assertEquals(accountDetails.getProfileId(), result.getProfileId());
        //                    assertEquals(accountDetails.getPassportId(), result.getPassportId());
        //                    assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
        //                    assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
        //                    assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        //                }
        //        );
        assertAll(() -> {
            assertEquals(accountDetails.getId(), result.getId());
            assertEquals(accountDetails.getMoney(), result.getMoney());
            assertEquals(accountDetails.getProfileId(), result.getProfileId());
            assertEquals(accountDetails.getPassportId(), result.getPassportId());
            assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
            assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
            assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        });
    }

    @Test
    // TODO переименуй в  "обновление с id равным null, негативный сценарий"
    @DisplayName("обновление с id = null")
    void updateIdIsNullNegativeTest() {
        when(repository.findById(any()))
                .thenThrow(new IllegalArgumentException("The id must not be null!"));

        // TODO приведи к виду
        //  final IllegalArgumentException exception = assertThrows(
        //                IllegalArgumentException.class, () -> service.update(ONE, mapper.toDto(accountDetails))
        //        );
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.update(ONE, mapper.toDto(accountDetails)));

        assertEquals("The id must not be null!", exception.getMessage());
    }

    @Test
    // TODO переименуй в "обновление, негативный сценарий"
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        // TODO приведи к виду
        //  final EntityNotFoundException exception = assertThrows(
        //   EntityNotFoundException.class, () -> service.update(ONE, new AccountDetailsDto())
        //   );
        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(ONE, new AccountDetailsDto()));

        assertEquals("Не существующий id = " + ONE, exception.getMessage());
    }
}
