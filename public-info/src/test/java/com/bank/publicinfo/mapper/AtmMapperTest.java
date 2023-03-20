package com.bank.publicinfo.mapper;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.supplier.AtmSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AtmMapperTest extends ParentTest {

    private static AtmMapper mapper;

    private static AtmEntity atm;

    private static AtmDto atmDto;

    private static AtmDto atmUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new AtmMapperImpl();

        AtmSupplier atmSupplier = new AtmSupplier();

        atmDto = atmSupplier.getDto(ONE, SPACE, TIME, TIME, TRUE, null);

        atmUpdateDto = atmSupplier.getDto(null, SPACE, TIME, TIME, TRUE, null);

        atm = atmSupplier.getEntity(ONE, SPACE, TIME, TIME, TRUE, null);
    }

    @Test
    @DisplayName("Маппинг к entity")
    void toEntityTest() {
        final AtmEntity result = mapper.toEntity(atmDto);

        assertAll(() -> {
            assertNull(result.getId());
            assertNull(result.getBranch());
            assertEquals(atmDto.getAddress(), result.getAddress());
            assertEquals(atmDto.getStartOfWork(), result.getStartOfWork());
            assertEquals(atmDto.getEndOfWork(), result.getEndOfWork());
            assertEquals(atmDto.getAllHours(), result.getAllHours());
        });
    }

    @Test
    @DisplayName("Маппинг к entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг к dto")
    void toDtoTest() {
        final AtmDto result = mapper.toDto(atm);

        assertAll(() -> {
            assertNull(result.getBranch());
            assertEquals(atmDto.getId(), result.getId());
            assertEquals(atmDto.getAddress(), result.getAddress());
            assertEquals(atmDto.getStartOfWork(), result.getStartOfWork());
            assertEquals(atmDto.getEndOfWork(), result.getEndOfWork());
            assertEquals(atmDto.getAllHours(), result.getAllHours());
        });
    }

    @Test
    @DisplayName("Маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        final AtmEntity result = mapper.mergeToEntity(atmUpdateDto, atm);

        assertAll(
                () -> {
                    assertNull(result.getBranch());
                    assertEquals(atm.getId(), result.getId());
                    assertEquals(atm.getAddress(), result.getAddress());
                    assertEquals(atm.getStartOfWork(), result.getStartOfWork());
                    assertEquals(atm.getEndOfWork(), result.getEndOfWork());
                    assertEquals(atm.getAllHours(), result.getAllHours());
                }
        );
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final AtmEntity result = mapper.mergeToEntity(null, atm);

        assertAll(
                () -> {
                    assertNull(result.getBranch());
                    assertEquals(atm.getId(), result.getId());
                    assertEquals(atm.getAddress(), result.getAddress());
                    assertEquals(atm.getStartOfWork(), result.getEndOfWork());
                    assertEquals(atm.getAllHours(), result.getAllHours());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к списку дто")
    void toDtoListTest() {
        final List<AtmDto> atms = mapper.toDtoList(List.of(atm));

        final AtmDto result = atms.get(0);

        assertAll(
                () -> {
                    assertNull(result.getBranch());
                    assertEquals(ONE, atms.size());
                    assertEquals(atm.getId(), result.getId());
                    assertEquals(atm.getAddress(), result.getAddress());
                    assertEquals(atm.getStartOfWork(), result.getStartOfWork());
                    assertEquals(atm.getEndOfWork(), result.getEndOfWork());
                    assertEquals(atm.getAllHours(), result.getAllHours());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к списку дто, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("Маппинг к списку dto, один из элементов списка равен null")
    void toListDtoElementNullTest() {
        final List<AtmEntity> atms = new ArrayList<>();
        atms.add(atm);
        atms.add(null);

        final List<AtmDto> actually = mapper.toDtoList(atms);
        final var zeroIndexResult = actually.get(0);

        assertAll(
                () -> {
                    assertNull(actually.get(1));
                    assertNull(zeroIndexResult.getBranch());
                    assertEquals(TWO, actually.size());
                    assertEquals(atm.getId(), zeroIndexResult.getId());
                    assertEquals(atm.getAddress(), zeroIndexResult.getAddress());
                    assertEquals(atm.getStartOfWork(), zeroIndexResult.getStartOfWork());
                    assertEquals(atm.getEndOfWork(), zeroIndexResult.getEndOfWork());
                    assertEquals(atm.getAllHours(), zeroIndexResult.getAllHours());
                }
        );
    }
}
