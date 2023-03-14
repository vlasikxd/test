package com.bank.publicinfo.service;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;
import com.bank.publicinfo.mapper.LicenseMapperImpl;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.impl.LicenseServiceImpl;
import com.bank.publicinfo.supplier.LicenseSupplier;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class LicenseServiceTest extends ParentTest {

    private static LicenseEntity license;

    private static LicenseEntity updateLicense;

    private static LicenseDto updateLicenseDto;

    @InjectMocks
    private LicenseServiceImpl service;

    @Mock
    private LicenseRepository repository;
    @Spy
    private LicenseMapperImpl mapper;
    @Spy
    private Validator validator;

    @BeforeAll
    static void init() {
        LicenseSupplier licenseSupplier = new LicenseSupplier();

        license = licenseSupplier.getEntity(ONE, BYTE, null);

        updateLicense = licenseSupplier.getEntity(ONE, BYTE, null);

        updateLicenseDto = licenseSupplier.getDto(ONE, BYTE, null);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final LicenseDto result = service.save(updateLicenseDto);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(updateLicenseDto.getId(), result.getId());
                    assertEquals(updateLicenseDto.getPhoto(), result.getPhoto());
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

        final LicenseDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(license.getId(), result.getId());
                    assertEquals(license.getPhoto(), result.getPhoto());
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

        assertEquals("license с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updateTest() {
        saveMock();
        findByIdMock();

        final LicenseDto result = service.update(ONE, updateLicenseDto);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(updateLicenseDto.getId(), result.getId());
                    assertEquals(updateLicenseDto.getPhoto(), result.getPhoto());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан dto равный null, позитивный сценарий")
    void updateDtoNullPositiveTest() {
        findByIdMock();
        doReturn(license).when(repository).save(any());

        final LicenseDto result = service.update(ONE, null);

        assertAll(() -> {
            assertNull(result.getBankDetails());
            assertEquals(license.getId(), result.getId());
            assertEquals(license.getPhoto(), result.getPhoto());
        });
    }

    @Test
    @DisplayName("обновление, негативный сценарий")
    void updateNegativeTest() {
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new LicenseDto())
        );

        assertEquals("Обновление невозможно, license не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllTest() {

        final List<LicenseDto> licenses = readAllTestPrepare();
        final var zeroLicense = licenses.get(0);
        final var firstLicense = licenses.get(1);

        assertAll(
                () -> {
                    assertEquals(TWO, licenses.size());
                    assertNull(zeroLicense.getBankDetails());
                    assertNull(firstLicense.getBankDetails());
                    assertEquals(license.getId(), zeroLicense.getId());
                    assertEquals(license.getId(), firstLicense.getId());
                    assertEquals(updateLicense.getPhoto(), zeroLicense.getPhoto());
                    assertEquals(updateLicense.getPhoto(), firstLicense.getPhoto());
                }
        );
    }

    private List<LicenseDto> readAllTestPrepare() {
        doReturn(List.of(license, updateLicense)).when(repository).findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id, негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new LicenseEntity())).when(repository).findAllById(any());

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, license не существуют(ет)", exception.getMessage());
    }

    private void saveMock() {
        doReturn(updateLicense).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(license)).when(repository).findById(ONE);
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
