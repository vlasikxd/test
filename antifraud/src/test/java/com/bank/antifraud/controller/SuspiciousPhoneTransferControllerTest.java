package com.bank.antifraud.controller;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import com.bank.antifraud.supplier.SuspiciousPhoneTransferSupplier;
import com.bank.common.exception.ValidationException;
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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
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

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@WebMvcTest(SuspiciousPhoneTransferController.class)
public class SuspiciousPhoneTransferControllerTest extends ParentTest {

    private static SuspiciousPhoneTransferDto suspiciousPhoneTransfer;
    private static SuspiciousPhoneTransferSupplier suspiciousPhoneTransferSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private SuspiciousPhoneTransferService service;

    @BeforeAll
    static void init() {
        suspiciousPhoneTransferSupplier = new SuspiciousPhoneTransferSupplier();

        suspiciousPhoneTransfer = suspiciousPhoneTransferSupplier.getDto(ONE, ONE, TRUE, TRUE);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(suspiciousPhoneTransfer).when(service).create(any());

        final int phoneTransferId = getIntFromLong(
                suspiciousPhoneTransfer.getPhoneTransferId()
        );

        mockMvc.perform(
                post("/suspicious/phone/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousPhoneTransfer))
        ).andExpectAll(status().isOk(),
                jsonPath("$.phoneTransferId", is(phoneTransferId)),
                jsonPath("$.isBlocked", is(suspiciousPhoneTransfer.getIsBlocked())),
                jsonPath("$.isSuspicious", is(suspiciousPhoneTransfer.getIsSuspicious())),
                jsonPath("$.blockedReason", is(suspiciousPhoneTransfer.getBlockedReason())),
                jsonPath("$.suspiciousReason", is(suspiciousPhoneTransfer.getSuspiciousReason()))
        );
    }

    @Test
    @DisplayName("сохранение невалидного значения, негативный сценарий")
    void createNoValidNegativeTest() throws Exception {
        final String exceptionMessage = "Дублирование значения уникального поля";

        doThrow(new ValidationException(exceptionMessage)).when(service).create(any());

        mockMvc.perform(
                post("/suspicious/phone/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousPhoneTransfer))
        ).andExpectAll(status().isUnprocessableEntity(),
                content().string(exceptionMessage)
        );
    }

    @Test
    @DisplayName("сохранение, передача pdf вместо json, негативный сценарий")
    void createWrongMediaTypeNegativeTest() throws Exception {
        mockMvc.perform(
                post("/suspicious/phone/transfer")
                        .contentType(MediaType.APPLICATION_PDF)
                        .content(mapper.writeValueAsString(suspiciousPhoneTransfer))
        ).andExpectAll(
                status().isInternalServerError()
        );
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(suspiciousPhoneTransfer).when(service).read(any());

        final int phoneTransferId = getIntFromLong(
                suspiciousPhoneTransfer.getPhoneTransferId()
        );

        mockMvc.perform(
                        get("/suspicious/phone/transfer/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.phoneTransferId", is(phoneTransferId)),
                        jsonPath("$.isBlocked", is(suspiciousPhoneTransfer.getIsBlocked())),
                        jsonPath("$.isSuspicious", is(suspiciousPhoneTransfer.getIsSuspicious())),
                        jsonPath("$.blockedReason", is(suspiciousPhoneTransfer.getBlockedReason())),
                        jsonPath("$.suspiciousReason", is(suspiciousPhoneTransfer.getSuspiciousReason()))
                );
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(ONE, SUSPICIOUS_PHONE_TRANSFER_NAME)))
                .when(service).read(any());

        mockMvc.perform(
                        get("/suspicious/phone/transfer/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(getNotFoundExceptionMessage(ONE, SUSPICIOUS_PHONE_TRANSFER_NAME))
                );
    }

    @Test
    @DisplayName("чтение по неверному параметру, негативный сценарий)")
    void readIncorrectParamNegativeTest() throws Exception {
        mockMvc.perform(
                        get("/suspicious/phone/transfer/{id}", ONE_AND_HALF))
                .andExpectAll(
                        status().isInternalServerError()
                );
    }

    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<SuspiciousPhoneTransferDto> suspiciousPhoneTransfers = readAllTestPrepare();

        final var oneIndexTransfer = suspiciousPhoneTransfers.get(1);
        final var zeroIndexTransfer = suspiciousPhoneTransfers.get(0);

        final int oneIndexPhoneTransferId = getIntFromLong(oneIndexTransfer.getPhoneTransferId());
        final int zeroIndexPhoneTransferId = getIntFromLong(zeroIndexTransfer.getPhoneTransferId());

        mockMvc.perform(get("/suspicious/phone/transfer?id=1&id=2"))
                .andExpectAll(status().isOk(),
                        jsonPath("$", hasSize(suspiciousPhoneTransfers.size())),
                        jsonPath("$.[0].phoneTransferId", is(zeroIndexPhoneTransferId)),
                        jsonPath("$.[0].isBlocked", is(zeroIndexTransfer.getIsBlocked())),
                        jsonPath("$.[0].isSuspicious", is(zeroIndexTransfer.getIsSuspicious())),
                        jsonPath("$.[0].blockedReason", is(zeroIndexTransfer.getBlockedReason())),
                        jsonPath("$.[0].suspiciousReason", is(zeroIndexTransfer.getSuspiciousReason())),
                        jsonPath("$.[1].phoneTransferId", is(oneIndexPhoneTransferId)),
                        jsonPath("$.[1].isBlocked", is(oneIndexTransfer.getIsBlocked())),
                        jsonPath("$.[1].isSuspicious", is(oneIndexTransfer.getIsSuspicious())),
                        jsonPath("$.[1].blockedReason", is(oneIndexTransfer.getBlockedReason())),
                        jsonPath("$.[1].suspiciousReason", is(oneIndexTransfer.getSuspiciousReason()))
                );
    }

    @Test
    @DisplayName("чтение по списку несуществующих id, негативный сценарий")
    void readAllNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(TWO, SUSPICIOUS_PHONE_TRANSFER_NAME)))
                .when(service).readAll(any());

        mockMvc.perform(
                        get("/suspicious/phone/transfer?id=" + TWO))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(getNotFoundExceptionMessage(TWO, SUSPICIOUS_PHONE_TRANSFER_NAME))
                );
    }

    private List<SuspiciousPhoneTransferDto> readAllTestPrepare() {
        final List<SuspiciousPhoneTransferDto> suspiciousPhoneTransfers = List.of(
                suspiciousPhoneTransferSupplier.getDto(ONE, ONE, TRUE, TRUE),
                suspiciousPhoneTransferSupplier.getDto(TWO, TWO, FALSE, TRUE)
        );

        doReturn(suspiciousPhoneTransfers).when(service).readAll(any());

        return suspiciousPhoneTransfers;
    }

    @Test
    @DisplayName("чтение по списку, передача строки вместо id, негативный сценарий")
    void readAllWrongIdsNegativeTest() throws Exception {
        doThrow(new IllegalArgumentException()).when(service).readAll(any());

        mockMvc.perform(
                        get("/suspicious/phone/transfer?id=6&id=4&id=String"))
                .andExpectAll(
                        status().isInternalServerError()
                );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(suspiciousPhoneTransfer).when(service).update(any(), anyLong());

        final int phoneTransferId = getIntFromLong(suspiciousPhoneTransfer.getPhoneTransferId());

        mockMvc.perform(
                put("/suspicious/phone/transfer/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousPhoneTransfer))
        ).andExpectAll(status().isOk(),
                jsonPath("$.phoneTransferId", is(phoneTransferId)),
                jsonPath("$.isBlocked", is(suspiciousPhoneTransfer.getIsBlocked())),
                jsonPath("$.isSuspicious", is(suspiciousPhoneTransfer.getIsSuspicious())),
                jsonPath("$.blockedReason", is(suspiciousPhoneTransfer.getBlockedReason())),
                jsonPath("$.suspiciousReason", is(suspiciousPhoneTransfer.getSuspiciousReason()))
        );
    }

    @Test
    @DisplayName("обновление несуществующего перевода, негативный сценарий")
    void updateNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(ONE, SUSPICIOUS_PHONE_TRANSFER_NAME)))
                .when(service).update(any(), anyLong());

        mockMvc.perform(
                put("/suspicious/phone/transfer/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousPhoneTransfer))
        ).andExpectAll(
                status().isNotFound(),
                content().string(getNotFoundExceptionMessage(ONE, SUSPICIOUS_PHONE_TRANSFER_NAME))
        );
    }

    @Test
    @DisplayName("обновление pdf вместо json, негативный сценарий")
    void updateWrongMediaTypeNegativeTest() throws Exception {
        mockMvc.perform(
                put("/suspicious/phone/transfer/{id}", ONE)
                        .contentType(MediaType.APPLICATION_PDF)
                        .content(mapper.writeValueAsString(suspiciousPhoneTransfer))
        ).andExpectAll(
                status().isInternalServerError()
        );
    }
}
