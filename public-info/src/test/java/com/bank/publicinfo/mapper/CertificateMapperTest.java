package com.bank.publicinfo.mapper;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import com.bank.publicinfo.supplier.CertificateSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CertificateMapperTest extends ParentTest {

    private static CertificateMapper mapper;

    private static CertificateEntity certificate;

    private static CertificateDto certificateDto;

    private static CertificateDto certificateUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new CertificateMapperImpl();

        CertificateSupplier certificateSupplier = new CertificateSupplier();

        certificateDto = certificateSupplier.getDto(ONE, BYTE, null);

        certificateUpdateDto = certificateSupplier.getDto(null, BYTE, null);

        certificate = certificateSupplier.getEntity(ONE, BYTE, null);
    }

    @Test
    @DisplayName("маппинг к entity")
    void toEntityTest() {
        final CertificateEntity result = mapper.toEntity(certificateDto);

        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertNull(result.getBankDetails());
                    assertEquals(certificateDto.getPhoto(), result.getPhoto());
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
        final CertificateDto result = mapper.toDto(certificate);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(certificate.getId(), result.getId());
                    assertEquals(certificate.getPhoto(), result.getPhoto());
                }
        );
    }

    @Test
    @DisplayName("маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("слияние в entity позитивный сценарий")
    void mergeToEntityTest() {
        final CertificateEntity result = mapper.mergeToEntity(certificateUpdateDto, certificate);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(certificate.getId(), result.getId());
                    assertEquals(certificate.getPhoto(), result.getPhoto());
                }
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final CertificateEntity result = mapper.mergeToEntity(null, certificate);

        assertAll(() -> {
            assertNull(result.getBankDetails());
            assertEquals(certificate.getId(), result.getId());
            assertEquals(certificate.getPhoto(), result.getPhoto());
        });
    }

    @Test
    @DisplayName("маппинг к списку дто")
    void toDtoListTest() {
        final List<CertificateDto> certificates = mapper.toDtoList(
                List.of(certificate)
        );

        final CertificateDto result = certificates.get(0);

        assertAll(
                () -> {
                    assertNull(result.getBankDetails());
                    assertEquals(ONE, certificates.size());
                    assertEquals(certificate.getId(), result.getId());
                    assertEquals(certificate.getPhoto(), result.getPhoto());
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
        final List<CertificateEntity> certificates = new ArrayList<>();
        certificates.add(certificate);
        certificates.add(null);

        final List<CertificateDto> actuallyList = mapper.toDtoList(certificates);
        final var zeroIndexResult = actuallyList.get(0);

        assertAll(
                () -> {
                    assertNull(actuallyList.get(1));
                    assertNull(zeroIndexResult.getBankDetails());
                    assertEquals(TWO, actuallyList.size());
                    assertEquals(certificate.getId(), zeroIndexResult.getId());
                    assertEquals(certificate.getPhoto(), zeroIndexResult.getPhoto());
                }
        );
    }
}
