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
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() throws Exception {
        doReturn(certificate).when(service).save(any());

        mockMvc.perform(post("/certificate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().isOk(),
                jsonPath("$.photo", is(getIntFromByte(certificate.getPhoto()))),
                jsonPath("$.bankDetails", is(certificate.getBankDetails()))
        );
    }

    @Test
    @DisplayName("сохранение некорректных данных, негативный сценарий")
    void saveInvalidDataNegativeTest() throws Exception {
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
    @DisplayName("сохранение pdf вместо json, негативный сценарий")
    void saveWrongMediaTypeNegativeTest() throws Exception {
        mockMvc.perform(
                post("/certificate")
                        .contentType(MediaType.APPLICATION_PDF)
                        .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().is5xxServerError());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(certificate).when(service).read(any());

        final int id = getIntFromLong(certificate.getId());

        mockMvc.perform(get("/certificate/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.photo", is(getIntFromByte(certificate.getPhoto()))),
                        jsonPath("$.bankDetails", is(certificate.getBankDetails()))
                );
    }

    @Test
    @DisplayName("чтение несуществующего сертификата, негативный сценарий")
    void readNotExistCertificateNegativeTest() throws Exception {
        String errorMessage = "Сертификата нет";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).read(any());

        mockMvc.perform(get("/certificate/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(errorMessage)
                );
    }

    @Test
    @DisplayName("чтение некорректного id, негативный сценарий")
    void readWrongIdNegativeTest() throws Exception {
        mockMvc.perform(get("/certificate/test"))
                .andExpectAll(status().is4xxClientError());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {

        final List<CertificateDto> certificates = returnCertificates();

        doReturn(certificates).when(service).readAll(any());

        final CertificateDto zeroCertificate = certificates.get(0);
        final CertificateDto oneCertificate = certificates.get(1);

        final int zeroId = getIntFromLong(zeroCertificate.getId());

        final int oneId = getIntFromLong(oneCertificate.getId());

        mockMvc.perform(get("/certificate?id=1&id=2")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(certificates.size())),
                jsonPath("$.[0].id", is(zeroId)),
                jsonPath("$.[0].photo", is(getIntFromByte(zeroCertificate.getPhoto()))),
                jsonPath("$.[0].bankDetails", is(zeroCertificate.getBankDetails())),
                jsonPath("$.[1].id", is(oneId)),
                jsonPath("$.[1].photo", is(getIntFromByte(oneCertificate.getPhoto()))),
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
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNotExistIdNegativeTest() throws Exception {
        String errorMessage = "Ошибка в параметрах";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).readAll(any());

        mockMvc.perform(get("/certificate?id=1")).
                andExpectAll(
                        status().isNotFound(),
                        content().string(errorMessage)
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, один из id некорректен, негативный сценарий")
    void readAllWrongIdNegativeTest() throws Exception {
        mockMvc.perform(get("/certificate?id=1&id=test"))
                .andExpectAll(status().is4xxClientError());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(certificate).when(service).update(anyLong(), any());

        final int id = getIntFromLong(certificate.getId());

        mockMvc.perform(put("/certificate/{id}", ONE).
                contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().isOk(),
                jsonPath("$.id", is(id)),
                jsonPath("$.photo", is(getIntFromByte(certificate.getPhoto()))),
                jsonPath("$.bankDetails", is(certificate.getBankDetails()))
        );
    }

    @Test
    @DisplayName("обновление несуществующего id, негативный сценарий")
    void updateNoExistIdNegativeTest() throws Exception {
        String errorMessage = "Обновление невозможно";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).update(anyLong(), any());

        mockMvc.perform(put("/certificate/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().isNotFound(),
                content().string(errorMessage)
        );
    }

    @Test
    @DisplayName("обновление некорректного id, негативный сценарий")
    void updateWrongIdNegativeTest() throws Exception {
        mockMvc.perform(put("/certificate/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(certificate))
        ).andExpectAll(status().is4xxClientError());
    }
}
