package com.bank.publicinfo.service;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.mapper.BankDetailsMapperImpl;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.service.impl.BankDetailsServiceImpl;
import com.bank.publicinfo.supplier.BankDetailsSupplier;
import com.bank.publicinfo.validator.Validator;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
public class BankDetailsServiceTest extends ParentTest {

    private static BankDetailsEntity bankDetails;

    private static BankDetailsEntity updateBankDetails;

    private static BankDetailsDto updateBankDetailsDto;

    @Mock
    BankDetailsRepository repository;

    @InjectMocks
    private BankDetailsServiceImpl service;

    @Spy
    private BankDetailsMapperImpl mapper;

    @Spy
    private Validator validator;

    @BeforeAll
    static void init() {
        BankDetailsSupplier bankDetailsSupplier = new BankDetailsSupplier();

        bankDetails = bankDetailsSupplier.getEntity(ONE, TWO, TWO, TWO);

        updateBankDetails = bankDetailsSupplier.getEntity(ONE, TWO, TWO, TWO);

        updateBankDetailsDto = bankDetailsSupplier.getDto(ONE, TWO, TWO, TWO);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final BankDetailsDto result = service.save(updateBankDetailsDto);

        assertAll(
                () -> {
                    assertEquals(updateBankDetailsDto.getId(), result.getId());
                    assertEquals(updateBankDetailsDto.getBik(), result.getBik());
                    assertEquals(updateBankDetailsDto.getInn(), result.getInn());
                    assertEquals(updateBankDetailsDto.getKpp(), result.getKpp());
                    assertEquals(updateBankDetailsDto.getCorAccount(), result.getCorAccount());
                    assertEquals(updateBankDetailsDto.getCity(), result.getCity());
                    assertEquals(updateBankDetailsDto.getJointStockCompany(), result.getJointStockCompany());
                    assertEquals(updateBankDetailsDto.getName(), result.getName());
                }
        );
    }

    @Test
    @DisplayName("сохранение, негативный сценарий")
    void saveNegativeTest() {
        doThrow(new IllegalArgumentException("Недопустимые параметры")).when(repository).save(any());

        final var exception = assertThrows(
                IllegalArgumentException.class, () -> service.save(null)
        );

        assertEquals("Недопустимые параметры", exception.getMessage());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() {
        findByIdMock();

        final BankDetailsDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertEquals(updateBankDetails.getId(), result.getId());
                    assertEquals(updateBankDetails.getBik(), result.getBik());
                    assertEquals(updateBankDetails.getInn(), result.getInn());
                    assertEquals(updateBankDetails.getKpp(), result.getKpp());
                    assertEquals(updateBankDetails.getCity(), result.getCity());
                    assertEquals(updateBankDetails.getName(), result.getName());
                    assertEquals(updateBankDetails.getCorAccount(), result.getCorAccount());
                    assertEquals(updateBankDetails.getJointStockCompany(), result.getJointStockCompany());
                }
        );
    }

    @Test
    @DisplayName("чтение, негативный сценарий")
    void readNegativeTest() {
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals("bankDetails с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updatePositiveTest() {
        saveMock();
        findByIdMock();

        final BankDetailsDto result = service.update(ONE, updateBankDetailsDto);

        assertAll(
                () -> {
                    assertEquals(updateBankDetails.getId(), result.getId());
                    assertEquals(updateBankDetails.getBik(), result.getBik());
                    assertEquals(updateBankDetails.getInn(), result.getInn());
                    assertEquals(updateBankDetails.getKpp(), result.getKpp());
                    assertEquals(updateBankDetails.getCity(), result.getCity());
                    assertEquals(updateBankDetails.getName(), result.getName());
                    assertEquals(updateBankDetails.getCorAccount(), result.getCorAccount());
                    assertEquals(updateBankDetails.getJointStockCompany(), result.getJointStockCompany());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан dto равный null, позитивный сценарий")
    void updateDtoNullPositiveTest() {
        doReturn(bankDetails).when(repository).save(any());
        findByIdMock();

        final BankDetailsDto result = service.update(ONE, null);

        assertAll(
                () -> {
                    assertEquals(bankDetails.getId(), result.getId());
                    assertEquals(bankDetails.getBik(), result.getBik());
                    assertEquals(bankDetails.getInn(), result.getInn());
                    assertEquals(bankDetails.getKpp(), result.getKpp());
                    assertEquals(bankDetails.getCity(), result.getCity());
                    assertEquals(bankDetails.getName(), result.getName());
                    assertEquals(bankDetails.getCorAccount(), result.getCorAccount());
                    assertEquals(bankDetails.getJointStockCompany(), result.getJointStockCompany());
                }
        );
    }

    @Test
    @DisplayName("обновление, негативный сценарий")
    void updateNegativeTest() {
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new BankDetailsDto())
        );

        assertEquals("Обновление невозможно, bankDetails не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<BankDetailsDto> bankDetailsList = readAllTestPrepare();

        final var zeroBankDetails = bankDetailsList.get(0);
        final var firstBankDetails = bankDetailsList.get(1);

        assertAll(
                () -> {
                    assertEquals(TWO, bankDetailsList.size());
                    assertEquals(bankDetails.getId(), zeroBankDetails.getId());
                    assertEquals(bankDetails.getId(), firstBankDetails.getId());
                    assertEquals(updateBankDetails.getBik(), zeroBankDetails.getBik());
                    assertEquals(updateBankDetails.getBik(), firstBankDetails.getBik());
                    assertEquals(updateBankDetails.getInn(), zeroBankDetails.getInn());
                    assertEquals(updateBankDetails.getInn(), firstBankDetails.getInn());
                    assertEquals(updateBankDetails.getKpp(), firstBankDetails.getKpp());
                    assertEquals(updateBankDetails.getKpp(), zeroBankDetails.getKpp());
                    assertEquals(updateBankDetails.getName(), firstBankDetails.getName());
                    assertEquals(updateBankDetails.getName(), zeroBankDetails.getName());
                    assertEquals(updateBankDetails.getCity(), zeroBankDetails.getCity());
                    assertEquals(updateBankDetails.getCity(), firstBankDetails.getCity());
                    assertEquals(updateBankDetails.getCorAccount(), zeroBankDetails.getCorAccount());
                    assertEquals(updateBankDetails.getCorAccount(), firstBankDetails.getCorAccount());
                    assertEquals(updateBankDetails.getJointStockCompany(), firstBankDetails.getJointStockCompany());
                    assertEquals(updateBankDetails.getJointStockCompany(), zeroBankDetails.getJointStockCompany());
                }
        );
    }

    private List<BankDetailsDto> readAllTestPrepare() {
        doReturn(List.of(bankDetails, updateBankDetails)).when(repository).findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id, негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new BankDetailsEntity())).when(repository).findAllById(any());

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, bankDetails не существуют(ет)", exception.getMessage());
    }

    private void saveMock() {
        doReturn(updateBankDetails).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(bankDetails)).when(repository).findById(ONE);
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
