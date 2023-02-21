package com.bank.profile.mapper;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.supplier.PassportSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PassportMapperTest extends ParentTest {

    private static PassportMapper mapper;

    private static PassportEntity passport;
    private static PassportDto passportDto;
    private static PassportDto passportUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new PassportMapperImpl();
        PassportSupplier passportSupplier = new PassportSupplier();

        passportDto = passportSupplier.getDto(ONE, INT_ONE, ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                LOCAL_DATE, WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null);

        passportUpdateDto = passportSupplier.getDto(null, INT_ONE, ONE, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, LOCAL_DATE, WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null);

        passport = passportSupplier.getEntity(ONE, INT_ONE, ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                LOCAL_DATE, WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null);
    }

    @Test
    @DisplayName("маппинг к entity позитивный сценарий")
    void toEntityTest() {
        final PassportEntity result = mapper.toEntity(passportDto);

        assertAll(() -> {
            assertNull(result.getId());
            assertNull(result.getRegistration());
            assertEquals(passportUpdateDto.getSeries(), result.getSeries());
            assertEquals(passportUpdateDto.getNumber(), result.getNumber());
            assertEquals(passportUpdateDto.getGender(), result.getGender());
            assertEquals(passportUpdateDto.getLastName(), result.getLastName());
            assertEquals(passportUpdateDto.getIssuedBy(), result.getIssuedBy());
            assertEquals(passportUpdateDto.getFirstName(), result.getFirstName());
            assertEquals(passportUpdateDto.getBirthDate(), result.getBirthDate());
            assertEquals(passportUpdateDto.getBirthPlace(), result.getBirthPlace());
            assertEquals(passportUpdateDto.getMiddleName(), result.getMiddleName());
            assertEquals(passportUpdateDto.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(passportUpdateDto.getDivisionCode(), result.getDivisionCode());
            assertEquals(passportUpdateDto.getExpirationDate(), result.getExpirationDate());
        });
    }

    @Test
    @DisplayName("маппинг к entity с параметром null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг к dto позитивный сценарий")
    void toDtoTest() {
        final PassportDto result = mapper.toDto(passport);

        assertAll(() -> {
            assertNull(result.getRegistration());
            assertEquals(passport.getId(), result.getId());
            assertEquals(passport.getSeries(), result.getSeries());
            assertEquals(passport.getNumber(), result.getNumber());
            assertEquals(passport.getGender(), result.getGender());
            assertEquals(passport.getLastName(), result.getLastName());
            assertEquals(passport.getIssuedBy(), result.getIssuedBy());
            assertEquals(passport.getFirstName(), result.getFirstName());
            assertEquals(passport.getBirthDate(), result.getBirthDate());
            assertEquals(passport.getMiddleName(), result.getMiddleName());
            assertEquals(passport.getBirthPlace(), result.getBirthPlace());
            assertEquals(passport.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(passport.getDivisionCode(), result.getDivisionCode());
            assertEquals(passport.getExpirationDate(), result.getExpirationDate());
        });
    }

    @Test
    @DisplayName("маппинг к dto с параметром null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("слияние в entity позитивный сценарий")
    void mergeToEntityPositiveTest() {
        final PassportEntity result = mapper.mergeToEntity(passportUpdateDto, passport);

        assertAll(() -> {
            assertNull(result.getRegistration());
            assertEquals(passport.getId(), result.getId());
            assertEquals(passport.getSeries(), result.getSeries());
            assertEquals(passport.getNumber(), result.getNumber());
            assertEquals(passport.getGender(), result.getGender());
            assertEquals(passport.getLastName(), result.getLastName());
            assertEquals(passport.getIssuedBy(), result.getIssuedBy());
            assertEquals(passport.getFirstName(), result.getFirstName());
            assertEquals(passport.getBirthDate(), result.getBirthDate());
            assertEquals(passport.getMiddleName(), result.getMiddleName());
            assertEquals(passport.getBirthPlace(), result.getBirthPlace());
            assertEquals(passport.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(passport.getDivisionCode(), result.getDivisionCode());
            assertEquals(passport.getExpirationDate(), result.getExpirationDate());
        });
    }

    @Test
    @DisplayName("слияние в entity, где dto равен null")
    void mergeToEntityNullTest() {
        final PassportEntity result = mapper.mergeToEntity(null, passport);

        assertAll(() -> {
            assertNull(result.getRegistration());
            assertEquals(passport.getId(), result.getId());
            assertEquals(passport.getSeries(), result.getSeries());
            assertEquals(passport.getNumber(), result.getNumber());
            assertEquals(passport.getGender(), result.getGender());
            assertEquals(passport.getLastName(), result.getLastName());
            assertEquals(passport.getIssuedBy(), result.getIssuedBy());
            assertEquals(passport.getFirstName(), result.getFirstName());
            assertEquals(passport.getBirthDate(), result.getBirthDate());
            assertEquals(passport.getMiddleName(), result.getMiddleName());
            assertEquals(passport.getBirthPlace(), result.getBirthPlace());
            assertEquals(passport.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(passport.getDivisionCode(), result.getDivisionCode());
            assertEquals(passport.getExpirationDate(), result.getExpirationDate());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto позитивный сценарий")
    void toDtoListTest() {
        final List<PassportDto> passports = mapper.toDtoList(List.of(passport));

        final PassportDto result = passports.get(0);

        assertAll(() -> {
            assertNull(result.getRegistration());
            assertEquals(ONE, passports.size());
            assertEquals(passport.getId(), result.getId());
            assertEquals(passport.getSeries(), result.getSeries());
            assertEquals(passport.getNumber(), result.getNumber());
            assertEquals(passport.getGender(), result.getGender());
            assertEquals(passport.getLastName(), result.getLastName());
            assertEquals(passport.getIssuedBy(), result.getIssuedBy());
            assertEquals(passport.getFirstName(), result.getFirstName());
            assertEquals(passport.getBirthDate(), result.getBirthDate());
            assertEquals(passport.getMiddleName(), result.getMiddleName());
            assertEquals(passport.getBirthPlace(), result.getBirthPlace());
            assertEquals(passport.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(passport.getDivisionCode(), result.getDivisionCode());
            assertEquals(passport.getExpirationDate(), result.getExpirationDate());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto c параметром null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("маппинг к списку dto, где один из элементов списка равен null")
    void toListDtoElementNullTest() {
        final List<PassportEntity> passports = new ArrayList<>();
        passports.add(passport);
        passports.add(null);

        final List<PassportDto> actually = mapper.toDtoList(passports);
        final var zeroIndexResult = actually.get(0);

        assertAll(() -> {
            assertNull(actually.get(1));
            assertNull(zeroIndexResult.getRegistration());

            assertEquals(TWO, actually.size());
            assertEquals(passport.getId(), zeroIndexResult.getId());
            assertEquals(passport.getSeries(), zeroIndexResult.getSeries());
            assertEquals(passport.getNumber(), zeroIndexResult.getNumber());
            assertEquals(passport.getGender(), zeroIndexResult.getGender());
            assertEquals(passport.getLastName(), zeroIndexResult.getLastName());
            assertEquals(passport.getIssuedBy(), zeroIndexResult.getIssuedBy());
            assertEquals(passport.getFirstName(), zeroIndexResult.getFirstName());
            assertEquals(passport.getBirthDate(), zeroIndexResult.getBirthDate());
            assertEquals(passport.getMiddleName(), zeroIndexResult.getMiddleName());
            assertEquals(passport.getBirthPlace(), zeroIndexResult.getBirthPlace());
            assertEquals(passport.getDateOfIssue(), zeroIndexResult.getDateOfIssue());
            assertEquals(passport.getDivisionCode(), zeroIndexResult.getDivisionCode());
            assertEquals(passport.getExpirationDate(), zeroIndexResult.getExpirationDate());
        });
    }
}
