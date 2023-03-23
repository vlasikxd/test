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
    private static ActualRegistrationEntity actualRegistration;
    private static ActualRegistrationDto actualRegistrationDto;
    private static ActualRegistrationDto updateRegistrationDto;

    @BeforeAll
    static void init() {
        mapper = new ActualRegistrationMapperImpl();

        ActualRegistrationSupplier actualRegistrationSupplier = new ActualRegistrationSupplier();

        actualRegistrationDto = actualRegistrationSupplier.getDto(ONE, WHITESPACE, TWO);

        updateRegistrationDto = actualRegistrationSupplier.getDto(null, WHITESPACE, TWO);

        actualRegistration = actualRegistrationSupplier.getEntity(ONE, WHITESPACE, TWO);
    }

    @Test
    @DisplayName("маппинг к entity")
    void toEntityTest() {
        final ActualRegistrationEntity result = mapper.toEntity(actualRegistrationDto);

        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertEquals(actualRegistrationDto.getCity(), result.getCity());
                    assertEquals(actualRegistrationDto.getIndex(), result.getIndex());
                    assertEquals(actualRegistrationDto.getRegion(), result.getRegion());
                    assertEquals(actualRegistrationDto.getStreet(), result.getStreet());
                    assertEquals(actualRegistrationDto.getCountry(), result.getCountry());
                    assertEquals(actualRegistrationDto.getDistrict(), result.getDistrict());
                    assertEquals(actualRegistrationDto.getLocality(), result.getLocality());
                    assertEquals(actualRegistrationDto.getHouseBlock(), result.getHouseBlock());
                    assertEquals(actualRegistrationDto.getFlatNumber(), result.getFlatNumber());
                    assertEquals(actualRegistrationDto.getHouseNumber(), result.getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("маппинг к entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг к dto")
    void toDtoTest() {
        final ActualRegistrationDto result = mapper.toDto(actualRegistration);

        assertAll(
                () -> {
                    assertEquals(actualRegistrationDto.getId(), result.getId());
                    assertEquals(actualRegistrationDto.getCity(), result.getCity());
                    assertEquals(actualRegistrationDto.getIndex(), result.getIndex());
                    assertEquals(actualRegistrationDto.getRegion(), result.getRegion());
                    assertEquals(actualRegistrationDto.getStreet(), result.getStreet());
                    assertEquals(actualRegistrationDto.getCountry(), result.getCountry());
                    assertEquals(actualRegistrationDto.getDistrict(), result.getDistrict());
                    assertEquals(actualRegistrationDto.getLocality(), result.getLocality());
                    assertEquals(actualRegistrationDto.getHouseBlock(), result.getHouseBlock());
                    assertEquals(actualRegistrationDto.getFlatNumber(), result.getFlatNumber());
                    assertEquals(actualRegistrationDto.getHouseNumber(), result.getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        final ActualRegistrationEntity result = mapper.mergeToEntity(updateRegistrationDto, actualRegistration);

        assertAll(
                () -> {
                    assertEquals(actualRegistration.getId(), result.getId());
                    assertEquals(actualRegistration.getCity(), result.getCity());
                    assertEquals(actualRegistration.getIndex(), result.getIndex());
                    assertEquals(actualRegistration.getRegion(), result.getRegion());
                    assertEquals(actualRegistration.getStreet(), result.getStreet());
                    assertEquals(actualRegistration.getCountry(), result.getCountry());
                    assertEquals(actualRegistration.getDistrict(), result.getDistrict());
                    assertEquals(actualRegistration.getLocality(), result.getLocality());
                    assertEquals(actualRegistration.getHouseBlock(), result.getHouseBlock());
                    assertEquals(actualRegistration.getFlatNumber(), result.getFlatNumber());
                    assertEquals(actualRegistration.getHouseNumber(), result.getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final ActualRegistrationEntity result = mapper.mergeToEntity(null, actualRegistration);

        assertAll(
                () -> {
                    assertEquals(actualRegistration.getId(), result.getId());
                    assertEquals(actualRegistration.getCity(), result.getCity());
                    assertEquals(actualRegistration.getIndex(), result.getIndex());
                    assertEquals(actualRegistration.getRegion(), result.getRegion());
                    assertEquals(actualRegistration.getStreet(), result.getStreet());
                    assertEquals(actualRegistration.getCountry(), result.getCountry());
                    assertEquals(actualRegistration.getDistrict(), result.getDistrict());
                    assertEquals(actualRegistration.getLocality(), result.getLocality());
                    assertEquals(actualRegistration.getHouseBlock(), result.getHouseBlock());
                    assertEquals(actualRegistration.getFlatNumber(), result.getFlatNumber());
                    assertEquals(actualRegistration.getHouseNumber(), result.getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("маппинг к списку dto")
    void toDtoListTest() {
        final List<ActualRegistrationDto> actualRegistrations = mapper.toDtoList(List.of(actualRegistration));

        final ActualRegistrationDto result = actualRegistrations.get(0);

        assertAll(
                () -> {
                    assertEquals(ONE, actualRegistrations.size());
                    assertEquals(actualRegistration.getId(), result.getId());
                    assertEquals(actualRegistration.getCity(), result.getCity());
                    assertEquals(actualRegistration.getIndex(), result.getIndex());
                    assertEquals(actualRegistration.getRegion(), result.getRegion());
                    assertEquals(actualRegistration.getStreet(), result.getStreet());
                    assertEquals(actualRegistration.getCountry(), result.getCountry());
                    assertEquals(actualRegistration.getDistrict(), result.getDistrict());
                    assertEquals(actualRegistration.getLocality(), result.getLocality());
                    assertEquals(actualRegistration.getHouseBlock(), result.getHouseBlock());
                    assertEquals(actualRegistration.getFlatNumber(), result.getFlatNumber());
                    assertEquals(actualRegistration.getHouseNumber(), result.getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("маппинг к списку dto, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("маппинг к списку dto, один из элементов списка null")
    void toListDtoElementNullTest() {
        final List<ActualRegistrationEntity> actualRegistrations = new ArrayList<>();
        actualRegistrations.add(actualRegistration);
        actualRegistrations.add(null);

        final List<ActualRegistrationDto> actually = mapper.toDtoList(actualRegistrations);
        final var zeroIndexResult = actually.get(0);

        assertAll(
                () -> {
                    assertNull(actually.get(1));
                    assertEquals(TWO, actually.size());
                    assertEquals(actualRegistration.getId(), zeroIndexResult.getId());
                    assertEquals(actualRegistration.getCity(), zeroIndexResult.getCity());
                    assertEquals(actualRegistration.getIndex(), zeroIndexResult.getIndex());
                    assertEquals(actualRegistration.getRegion(), zeroIndexResult.getRegion());
                    assertEquals(actualRegistration.getStreet(), zeroIndexResult.getStreet());
                    assertEquals(actualRegistration.getCountry(), zeroIndexResult.getCountry());
                    assertEquals(actualRegistration.getDistrict(), zeroIndexResult.getDistrict());
                    assertEquals(actualRegistration.getLocality(), zeroIndexResult.getLocality());
                    assertEquals(actualRegistration.getHouseBlock(), zeroIndexResult.getHouseBlock());
                    assertEquals(actualRegistration.getFlatNumber(), zeroIndexResult.getFlatNumber());
                    assertEquals(actualRegistration.getHouseNumber(), zeroIndexResult.getHouseNumber());
                }
        );
    }
}
