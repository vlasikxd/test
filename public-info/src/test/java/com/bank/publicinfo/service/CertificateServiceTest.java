package com.bank.publicinfo.service;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import com.bank.publicinfo.mapper.CertificateMapperImpl;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.impl.CertificateServiceImpl;
import com.bank.publicinfo.supplier.CertificateSupplier;
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

public class CertificateServiceTest extends ParentTest {

    private static CertificateEntity certificate;

    private static CertificateEntity updateCertificate;

    private static CertificateDto updateCertificateDto;

    @InjectMocks
    private CertificateServiceImpl service;

    @Mock
    CertificateRepository repository;
    @Spy
    private CertificateMapperImpl mapper;
    @Spy
    private Validator validator;

    @BeforeAll
    static void init() {
        CertificateSupplier certificateSupplier = new CertificateSupplier();

        certificate = certificateSupplier.getEntity(ONE, BYTE, null);

        updateCertificate = certificateSupplier.getEntity(ONE, BYTE, null);

        updateCertificateDto = certificateSupplier.getDto(ONE, BYTE, null);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final CertificateDto result = service.save(updateCertificateDto);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(updateCertificateDto.getId(), result.getId());
                    assertEquals(updateCertificateDto.getPhoto(), result.getPhoto());
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

        final CertificateDto result = service.read(ONE);

        assertAll(() -> {
            assertNull(result.getBankDetails());
            assertEquals(certificate.getId(), result.getId());
            assertEquals(certificate.getPhoto(), result.getPhoto());
        });
    }

    @Test
    @DisplayName("чтение, негативный сценарий")
    void readNegativeTest() {
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals("certificate с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updateTest() {
        saveMock();
        findByIdMock();

        final CertificateDto result = service.update(ONE, updateCertificateDto);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(updateCertificateDto.getId(), result.getId());
                    assertEquals(updateCertificateDto.getPhoto(), result.getPhoto());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан dto равный null, позитивный сценарий")
    void updateDtoNullPositiveTest() {
        findByIdMock();
        doReturn(certificate).when(repository).save(any());

        final CertificateDto result = service.update(ONE, null);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(certificate.getId(), result.getId());
                    assertEquals(certificate.getPhoto(), result.getPhoto());
                }
        );
    }

    @Test
    @DisplayName("обновление, негативный сценарий")
    void updateNegativeTest() {
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new CertificateDto())
        );

        assertEquals("Обновление невозможно, certificate не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() {

        final List<CertificateDto> certificates = readAllTestPrepare();
        final var zeroCertificate = certificates.get(0);
        final var firstCertificate = certificates.get(1);

        assertAll(
                () -> {
                    assertNull(zeroCertificate.getBankDetails());
                    assertNull(firstCertificate.getBankDetails());
                    assertEquals(TWO, certificates.size());
                    assertEquals(certificate.getId(), zeroCertificate.getId());
                    assertEquals(certificate.getId(), firstCertificate.getId());
                    assertEquals(updateCertificate.getPhoto(), firstCertificate.getPhoto());
                    assertEquals(updateCertificate.getPhoto(), zeroCertificate.getPhoto());
                }
        );
    }

    private List<CertificateDto> readAllTestPrepare() {
        doReturn(List.of(certificate, updateCertificate)).when(repository).findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id, негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new CertificateEntity())).when(repository).findAllById(any());

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, certificate не существуют(ет)", exception.getMessage());
    }

    private void saveMock() {
        doReturn(updateCertificate).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(certificate)).when(repository).findById(ONE);
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}