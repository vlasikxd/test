package com.bank.profile.mapper;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.supplier.ActualRegistrationSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ActualRegistrationMapperTest extends ParentTest {

    private static ActualRegistrationMapper mapper;

    private static ActualRegistrationEntity registration;
    private static ActualRegistrationDto registrationDto;
    private static ActualRegistrationDto registrationUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new ActualRegistrationMapperImpl();
        ActualRegistrationSupplier actualRegistrationSupplier = new ActualRegistrationSupplier();

        registrationDto = actualRegistrationSupplier.getDto(ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO);

        registrationUpdateDto = actualRegistrationSupplier.getDto(null, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO);

        registration = actualRegistrationSupplier.getEntity(ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO);
    }

    @Test
    @DisplayName("маппинг к entity позитивный сценарий")
    void toEntityTest() {
        final ActualRegistrationEntity result = mapper.toEntity(registrationDto);

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
        final ActualRegistrationDto result = mapper.toDto(registration);

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
        final ActualRegistrationEntity result = mapper.mergeToEntity(registrationUpdateDto, registration);

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
        final ActualRegistrationEntity result = mapper.mergeToEntity(null, registration);

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
        final List<ActualRegistrationDto> actualRegistrations = mapper.toDtoList(List.of(registration));

        final ActualRegistrationDto result = actualRegistrations.get(0);

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
        final List<ActualRegistrationEntity> actualRegistrations = new ArrayList<>();
        actualRegistrations.add(registration);
        actualRegistrations.add(null);

        final List<ActualRegistrationDto> actually = mapper.toDtoList(actualRegistrations);
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
