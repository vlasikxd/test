package com.bank.profile.mapper;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.supplier.RegistrationSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RegistrationMapperTest extends ParentTest {

    private static RegistrationMapper mapper;

    private static RegistrationEntity registration;
    private static RegistrationDto registrationDto;
    private static RegistrationDto registrationUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new RegistrationMapperImpl();
        RegistrationSupplier registrationSupplier = new RegistrationSupplier();

        registrationDto = registrationSupplier.getDto(ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO);

        registrationUpdateDto = registrationSupplier.getDto(null, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO);

        registration = registrationSupplier.getEntity(ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO);
    }

    @Test
    @DisplayName("маппинг к entity позитивный сценарий")
    void toEntityTest() {
        final RegistrationEntity result = mapper.toEntity(registrationDto);

        assertAll(() -> {
            assertNull(result.getId());
            assertEquals(registrationDto.getCity(), result.getCity());
            assertEquals(registrationDto.getIndex(), result.getIndex());
            assertEquals(registrationDto.getRegion(), result.getRegion());
            assertEquals(registrationDto.getStreet(), result.getStreet());
            assertEquals(registrationDto.getCountry(), result.getCountry());
            assertEquals(registrationDto.getDistrict(), result.getDistrict());
            assertEquals(registrationDto.getLocality(), result.getLocality());
            assertEquals(registrationDto.getHouseBlock(), result.getHouseBlock());
            assertEquals(registrationDto.getFlatNumber(), result.getFlatNumber());
            assertEquals(registrationDto.getHouseNumber(), result.getHouseNumber());
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
        final RegistrationDto result = mapper.toDto(registration);

        assertAll(() -> {
            assertEquals(registrationDto.getId(), result.getId());
            assertEquals(registrationDto.getCity(), result.getCity());
            assertEquals(registrationDto.getIndex(), result.getIndex());
            assertEquals(registrationDto.getRegion(), result.getRegion());
            assertEquals(registrationDto.getStreet(), result.getStreet());
            assertEquals(registrationDto.getCountry(), result.getCountry());
            assertEquals(registrationDto.getDistrict(), result.getDistrict());
            assertEquals(registrationDto.getLocality(), result.getLocality());
            assertEquals(registrationDto.getHouseBlock(), result.getHouseBlock());
            assertEquals(registrationDto.getFlatNumber(), result.getFlatNumber());
            assertEquals(registrationDto.getHouseNumber(), result.getHouseNumber());
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
        final RegistrationEntity result = mapper.mergeToEntity(registrationUpdateDto, registration);

        assertAll(() -> {
            assertEquals(registration.getId(), result.getId());
            assertEquals(registration.getCity(), result.getCity());
            assertEquals(registration.getIndex(), result.getIndex());
            assertEquals(registration.getRegion(), result.getRegion());
            assertEquals(registration.getStreet(), result.getStreet());
            assertEquals(registration.getCountry(), result.getCountry());
            assertEquals(registration.getDistrict(), result.getDistrict());
            assertEquals(registration.getLocality(), result.getLocality());
            assertEquals(registration.getHouseBlock(), result.getHouseBlock());
            assertEquals(registration.getFlatNumber(), result.getFlatNumber());
            assertEquals(registration.getHouseNumber(), result.getHouseNumber());
        });
    }

    @Test
    @DisplayName("слияние в entity, где dto равен null")
    void mergeToEntityNullTest() {
        final RegistrationEntity result = mapper.mergeToEntity(null, registration);

        assertAll(() -> {
            assertEquals(registration.getId(), result.getId());
            assertEquals(registration.getCity(), result.getCity());
            assertEquals(registration.getIndex(), result.getIndex());
            assertEquals(registration.getRegion(), result.getRegion());
            assertEquals(registration.getStreet(), result.getStreet());
            assertEquals(registration.getCountry(), result.getCountry());
            assertEquals(registration.getDistrict(), result.getDistrict());
            assertEquals(registration.getLocality(), result.getLocality());
            assertEquals(registration.getHouseBlock(), result.getHouseBlock());
            assertEquals(registration.getFlatNumber(), result.getFlatNumber());
            assertEquals(registration.getHouseNumber(), result.getHouseNumber());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto позитивный сценарий")
    void toDtoListTest() {
        final List<RegistrationDto> actualRegistrations = mapper.toDtoList(List.of(registration));

        final RegistrationDto result = actualRegistrations.get(0);

        assertAll(() -> {
            assertEquals(ONE, actualRegistrations.size());
            assertEquals(registration.getId(), result.getId());
            assertEquals(registration.getCity(), result.getCity());
            assertEquals(registration.getIndex(), result.getIndex());
            assertEquals(registration.getRegion(), result.getRegion());
            assertEquals(registration.getStreet(), result.getStreet());
            assertEquals(registration.getCountry(), result.getCountry());
            assertEquals(registration.getDistrict(), result.getDistrict());
            assertEquals(registration.getLocality(), result.getLocality());
            assertEquals(registration.getHouseBlock(), result.getHouseBlock());
            assertEquals(registration.getFlatNumber(), result.getFlatNumber());
            assertEquals(registration.getHouseNumber(), result.getHouseNumber());
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
        final List<RegistrationEntity> actualRegistrations = new ArrayList<>();
        actualRegistrations.add(registration);
        actualRegistrations.add(null);

        final List<RegistrationDto> actually = mapper.toDtoList(actualRegistrations);
        final var zeroIndexResult = actually.get(0);

        assertAll(() -> {
            assertNull(actually.get(1));
            assertEquals(TWO, actually.size());
            assertEquals(registration.getId(), zeroIndexResult.getId());
            assertEquals(registration.getCity(), zeroIndexResult.getCity());
            assertEquals(registration.getIndex(), zeroIndexResult.getIndex());
            assertEquals(registration.getRegion(), zeroIndexResult.getRegion());
            assertEquals(registration.getStreet(), zeroIndexResult.getStreet());
            assertEquals(registration.getCountry(), zeroIndexResult.getCountry());
            assertEquals(registration.getDistrict(), zeroIndexResult.getDistrict());
            assertEquals(registration.getLocality(), zeroIndexResult.getLocality());
            assertEquals(registration.getHouseBlock(), zeroIndexResult.getHouseBlock());
            assertEquals(registration.getFlatNumber(), zeroIndexResult.getFlatNumber());
            assertEquals(registration.getHouseNumber(), zeroIndexResult.getHouseNumber());
        });
    }
}
