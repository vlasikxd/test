package com.bank.publicinfo.mapper;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.supplier.BankDetailsSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BankDetailsMapperTest extends ParentTest {

    private static BankDetailsMapper mapper;

    private static BankDetailsEntity bankDetails;

    private static BankDetailsDto bankDetailsDto;

    private static BankDetailsDto bankDetailsUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new BankDetailsMapperImpl();

        BankDetailsSupplier bankDetailsSupplier = new BankDetailsSupplier();

        bankDetailsDto = bankDetailsSupplier.getDto(ONE, TWO, TWO, TWO, INT_ONE, SPACE, SPACE, SPACE);

        bankDetailsUpdateDto = bankDetailsSupplier.getDto(null, TWO, TWO, TWO, INT_ONE, SPACE, SPACE, SPACE);

        bankDetails = bankDetailsSupplier.getEntity(ONE, TWO, TWO, TWO, INT_ONE, SPACE, SPACE, SPACE);
    }

    @Test
    @DisplayName("Маппинг к entity")
    void toEntityTest() {
        final BankDetailsEntity result = mapper.toEntity(bankDetailsDto);

        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertEquals(bankDetailsDto.getBik(), result.getBik());
                    assertEquals(bankDetailsDto.getInn(), result.getInn());
                    assertEquals(bankDetailsDto.getKpp(), result.getKpp());
                    assertEquals(bankDetailsDto.getCity(), result.getCity());
                    assertEquals(bankDetailsDto.getName(), result.getName());
                    assertEquals(bankDetailsDto.getCorAccount(), result.getCorAccount());
                    assertEquals(bankDetailsDto.getJointStockCompany(), result.getJointStockCompany());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг к dto")
    void toDtoTest() {

        final BankDetailsDto result = mapper.toDto(bankDetails);

        assertAll(
                () -> {
                    assertEquals(bankDetailsDto.getId(), result.getId());
                    assertEquals(bankDetailsDto.getBik(), result.getBik());
                    assertEquals(bankDetailsDto.getInn(), result.getInn());
                    assertEquals(bankDetailsDto.getKpp(), result.getKpp());
                    assertEquals(bankDetailsDto.getCorAccount(), result.getCorAccount());
                    assertEquals(bankDetailsDto.getCity(), result.getCity());
                    assertEquals(bankDetailsDto.getJointStockCompany(), result.getJointStockCompany());
                    assertEquals(bankDetailsDto.getName(), result.getName());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityPositiveTest() {

        final BankDetailsEntity result = mapper.mergeToEntity(bankDetailsUpdateDto, bankDetails);

        assertAll(
                () -> {
                    assertEquals(bankDetails.getId(), result.getId());
                    assertEquals(bankDetails.getBik(), result.getBik());
                    assertEquals(bankDetails.getInn(), result.getInn());
                    assertEquals(bankDetails.getKpp(), result.getKpp());
                    assertEquals(bankDetails.getCorAccount(), result.getCorAccount());
                    assertEquals(bankDetails.getCity(), result.getCity());
                    assertEquals(bankDetails.getJointStockCompany(), result.getJointStockCompany());
                    assertEquals(bankDetails.getName(), result.getName());
                }
        );
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final BankDetailsEntity result = mapper.mergeToEntity(null, bankDetails);

        assertAll(
                () -> {
                    assertEquals(bankDetails.getId(), result.getId());
                    assertEquals(bankDetails.getBik(), result.getBik());
                    assertEquals(bankDetails.getInn(), result.getInn());
                    assertEquals(bankDetails.getKpp(), result.getKpp());
                    assertEquals(bankDetails.getCorAccount(), result.getCorAccount());
                    assertEquals(bankDetails.getCity(), result.getCity());
                    assertEquals(bankDetails.getJointStockCompany(), result.getJointStockCompany());
                    assertEquals(bankDetails.getName(), result.getName());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к списку dto")
    void toDtoListTest() {
        final List<BankDetailsDto> bankDetailsList = mapper.toDtoList(
                List.of(bankDetails)
        );

        final BankDetailsDto result = bankDetailsList.get(0);

        assertAll(
                () -> {
                    assertEquals(ONE, bankDetailsList.size());
                    assertEquals(bankDetails.getId(), result.getId());
                    assertEquals(bankDetails.getBik(), result.getBik());
                    assertEquals(bankDetails.getInn(), result.getInn());
                    assertEquals(bankDetails.getKpp(), result.getKpp());
                    assertEquals(bankDetails.getCorAccount(), result.getCorAccount());
                    assertEquals(bankDetails.getCity(), result.getCity());
                    assertEquals(bankDetails.getJointStockCompany(), result.getJointStockCompany());
                    assertEquals(bankDetails.getName(), result.getName());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к списку dto, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("Маппинг к списку dto, один из элементов списка равен null")
    void toListDtoElementNullTest() {
        final List<BankDetailsEntity> bankDetailsList = new ArrayList<>();
        bankDetailsList.add(bankDetails);
        bankDetailsList.add(null);

        final List<BankDetailsDto> actually = mapper.toDtoList(bankDetailsList);
        final var zeroIndexResult = actually.get(0);

        assertAll(
                () -> {
                    assertNull(actually.get(1));
                    assertEquals(TWO, actually.size());
                    assertEquals(bankDetails.getId(), zeroIndexResult.getId());
                    assertEquals(bankDetails.getBik(), zeroIndexResult.getBik());
                    assertEquals(bankDetails.getInn(), zeroIndexResult.getInn());
                    assertEquals(bankDetails.getKpp(), zeroIndexResult.getKpp());
                    assertEquals(bankDetails.getCorAccount(), zeroIndexResult.getCorAccount());
                    assertEquals(bankDetails.getCity(), zeroIndexResult.getCity());
                    assertEquals(bankDetails.getJointStockCompany(), zeroIndexResult.getJointStockCompany());
                    assertEquals(bankDetails.getName(), zeroIndexResult.getName());
                }
        );
    }
}
