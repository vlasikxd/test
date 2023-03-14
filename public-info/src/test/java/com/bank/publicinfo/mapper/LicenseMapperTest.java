package com.bank.publicinfo.mapper;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;
import com.bank.publicinfo.supplier.LicenseSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LicenseMapperTest extends ParentTest {

    private static LicenseMapper mapper;

    private static LicenseEntity license;

    private static LicenseDto licenseDto;

    private static LicenseDto licenseUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new LicenseMapperImpl();

        LicenseSupplier licenseSupplier = new LicenseSupplier();

        licenseDto = licenseSupplier.getDto(ONE, BYTE, null);

        licenseUpdateDto = licenseSupplier.getDto(null, BYTE, null);

        license = licenseSupplier.getEntity(ONE, BYTE, null);
    }

    @Test
    @DisplayName("маппинг к entity")
    void toEntityTest() {
        final LicenseEntity result = mapper.toEntity(licenseDto);

        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertNull(result.getBankDetails());
                    assertEquals(licenseDto.getPhoto(), result.getPhoto());
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
        final LicenseDto result = mapper.toDto(license);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(license.getId(), result.getId());
                    assertEquals(license.getPhoto(), result.getPhoto());
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
        final LicenseEntity result = mapper.mergeToEntity(licenseUpdateDto, license);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(license.getId(), result.getId());
                    assertEquals(license.getPhoto(), result.getPhoto());
                }
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final LicenseEntity result = mapper.mergeToEntity(null, license);

        assertAll(() -> {
            assertNull(result.getBankDetails());
            assertEquals(license.getId(), result.getId());
            assertEquals(license.getPhoto(), result.getPhoto());
        });
    }

    @Test
    @DisplayName("маппинг к списку дто")
    void toDtoListTest() {
        final List<LicenseDto> licenses = mapper.toDtoList(
                List.of(license)
        );

        final LicenseDto result = licenses.get(0);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(ONE, licenses.size());
                    assertEquals(license.getId(), result.getId());
                    assertEquals(license.getPhoto(), result.getPhoto());
                }
        );
    }

    @Test
    @DisplayName("маппинг к списку дто, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("маппинг к списку dto, один из элементов списка равен null")
    void toListDtoElementNullTest() {
        final List<LicenseEntity> licenses = new ArrayList<>();
        licenses.add(license);
        licenses.add(null);

        final List<LicenseDto> actuallyList = mapper.toDtoList(licenses);
        final var zeroIndexResult = actuallyList.get(0);

        assertAll(
                () -> {
                    assertNull(actuallyList.get(1));
                    assertNull(zeroIndexResult.getBankDetails());
                    assertEquals(TWO, actuallyList.size());
                    assertEquals(license.getId(), zeroIndexResult.getId());
                    assertEquals(license.getPhoto(), zeroIndexResult.getPhoto());
                }
        );
    }
}
