package com.bank.publicinfo.controller;

import com.bank.common.exception.ValidationException;
import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
import com.bank.publicinfo.supplier.CertificateSupplier;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CertificateController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CertificateControllerTest extends ParentTest {

    private static CertificateDto certificate;
    private static CertificateSupplier certificateSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private CertificateService service;

    @BeforeAll
    static void init() {
        certificateSupplier = new CertificateSupplier();

        certificate = certificateSupplier.getDto(ONE, BYTE, null);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(certificate).when(service).save(any());

        final int photo = getIntFromByte(certificate.getPhoto());

        mockMvc.perform(post("/certificate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().isOk(),
                jsonPath("$.photo", is(photo)),
                jsonPath("$.bankDetails", is(certificate.getBankDetails()))
        );
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() throws Exception {
        String errorMessage = "Неверные данные";

        doThrow(new ValidationException(errorMessage)).when(service).save(any());

        mockMvc.perform(post("/certificate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().isUnprocessableEntity(),
                content().string(errorMessage)
        );
    }

    @Test
    @DisplayName("Чтение, позитивный сценарий")
    void readTest() throws Exception {
        doReturn(certificate).when(service).read(any());

        final int photo = getIntFromByte(certificate.getPhoto());
        final int id = getIntFromLong(certificate.getId());

        mockMvc.perform(get("/certificate/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.photo", is(photo)),
                        jsonPath("$.bankDetails", is(certificate.getBankDetails()))
                );
    }

    @Test
    @DisplayName("Чтение, негативный сценарий")
    void readNegativeTest() throws Exception {
        String errorMessage = "Сертификата нет";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).read(any());

        mockMvc.perform(get("/certificate/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(errorMessage)
                );
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {

        final List<CertificateDto> certificates = returnCertificates();

        doReturn(certificates).when(service).readAll(any());

        final CertificateDto zeroCertificate = certificates.get(0);
        final CertificateDto oneCertificate = certificates.get(1);

        final int zeroPhoto = getIntFromByte(zeroCertificate.getPhoto());
        final int zeroId = getIntFromLong(zeroCertificate.getId());

        final int onePhoto = getIntFromByte(oneCertificate.getPhoto());
        final int oneId = getIntFromLong(oneCertificate.getId());

        mockMvc.perform(get("/certificate?id=1&id=2")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(certificates.size())),
                jsonPath("$.[0].id", is(zeroId)),
                jsonPath("$.[0].photo", is(zeroPhoto)),
                jsonPath("$.[0].bankDetails", is(zeroCertificate.getBankDetails())),
                jsonPath("$.[1].id", is(oneId)),
                jsonPath("$.[1].photo", is(onePhoto)),
                jsonPath("$.[1].bankDetails", is(oneCertificate.getBankDetails()))
        );
    }

    private List<CertificateDto> returnCertificates() {
        return List.of(
                certificateSupplier.getDto(ONE, BYTE, null),
                certificateSupplier.getDto(ONE, BYTE, null)
        );
    }

    @Test
    @DisplayName("Чтение по нескольким id, негативный сценарий")
    void readAllNegativeTest() throws Exception {
        String errorMessage = "Ошибка в параметрах";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).readAll(any());

        mockMvc.perform(get("/certificate?id=1")).andExpectAll(status().isNotFound(),
                content().string(errorMessage)
        );
    }

    @Test
    @DisplayName("Обновление, позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(certificate).when(service).update(anyLong(), any());

        final int photo = getIntFromByte(certificate.getPhoto());
        final int id = getIntFromLong(certificate.getId());

        mockMvc.perform(put("/certificate/{id}", ONE).
                contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().isOk(),
                jsonPath("$.id", is(id)),
                jsonPath("$.photo", is(photo)),
                jsonPath("$.bankDetails", is(certificate.getBankDetails()))
        );
    }

    @Test
    @DisplayName("Обновление, негативный сценарий")
    void updateNegativeTest() throws Exception {
        String errorMessage = "Обновление невозможно";
        doThrow(new EntityNotFoundException(errorMessage)).when(service).update(anyLong(), any());

        mockMvc.perform(put("/certificate/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().isNotFound(),
                content().string(errorMessage)
        );
    }
}
