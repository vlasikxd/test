package com.bank.antifraud.controller;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.antifraud.supplier.SuspiciousAccountTransferSupplier;
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
@WebMvcTest(SuspiciousAccountTransferController.class)
public class SuspiciousAccountTransferControllerTest extends ParentTest {

    private static SuspiciousAccountTransferDto suspiciousAccountTransfer;
    private static SuspiciousAccountTransferSupplier suspiciousAccountTransferSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private SuspiciousAccountTransferService service;

    @BeforeAll
    static void init() {
        suspiciousAccountTransferSupplier = new SuspiciousAccountTransferSupplier();

        suspiciousAccountTransfer = suspiciousAccountTransferSupplier.getDto(ONE, ONE, TRUE, REASON);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(suspiciousAccountTransfer).when(service).create(any());

        final int accountTransferId = getIntFromLong(
                suspiciousAccountTransfer.getAccountTransferId()
        );

        mockMvc.perform(
                post("/suspicious/account/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousAccountTransfer))
        ).andExpectAll(status().isOk(),
                jsonPath("$.accountTransferId", is(accountTransferId)),
                jsonPath("$.isBlocked", is(suspiciousAccountTransfer.getIsBlocked())),
                jsonPath("$.isSuspicious", is(suspiciousAccountTransfer.getIsSuspicious())),
                jsonPath("$.blockedReason", is(suspiciousAccountTransfer.getBlockedReason())),
                jsonPath("$.suspiciousReason", is(suspiciousAccountTransfer.getSuspiciousReason()))
        );
    }

    @Test
    @DisplayName("сохранение невалидного значения, негативный сценарий")
    void createNoValidNegativeTest() throws Exception {

        final String exceptionMessage = "Дублирование значения уникального поля";

        doThrow(new ValidationException(exceptionMessage)).when(service).create(any());

        mockMvc.perform(
                post("/suspicious/account/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousAccountTransfer))
        ).andExpectAll(status().isUnprocessableEntity(),
                content().string(exceptionMessage)
        );
    }

    @Test
    @DisplayName("сохранение pdf вместо json, негативный сценарий")
    void createWrongMediaTypeNegativeTest() throws Exception {
        mockMvc.perform(
                post("/suspicious/account/transfer")
                        .contentType(MediaType.APPLICATION_PDF)
                        .content(mapper.writeValueAsString(suspiciousAccountTransfer))
        ).andExpectAll(status().isInternalServerError());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(suspiciousAccountTransfer).when(service).read(any());

        final int accountTransferId = getIntFromLong(
                suspiciousAccountTransfer.getAccountTransferId()
        );

        mockMvc.perform(
                        get("/suspicious/account/transfer/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.accountTransferId", is(accountTransferId)),
                        jsonPath("$.isBlocked", is(suspiciousAccountTransfer.getIsBlocked())),
                        jsonPath("$.isSuspicious", is(suspiciousAccountTransfer.getIsSuspicious())),
                        jsonPath("$.blockedReason", is(suspiciousAccountTransfer.getBlockedReason())),
                        jsonPath("$.suspiciousReason", is(suspiciousAccountTransfer.getSuspiciousReason()))
                );
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(ONE, SUSPICIOUS_ACCOUNT_TRANSFER_NAME)))
                .when(service).read(any());

        mockMvc.perform(
                get("/suspicious/account/transfer/{id}", ONE)
        ).andExpectAll(
                status().isNotFound(),
                content().string(getNotFoundExceptionMessage(ONE, SUSPICIOUS_ACCOUNT_TRANSFER_NAME))
        );
    }

    @Test
    @DisplayName("чтение по неверному параметру, негативный сценарий")
    void readIncorrectParamNegativeTest() throws Exception {
        mockMvc.perform(
                        get("/suspicious/account/transfer/{id}", ONE_AND_HALF))
                .andExpectAll(
                        status().isInternalServerError());
    }

    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<SuspiciousAccountTransferDto> suspiciousAccountTransfers = readAllTestPrepare();

        final var oneIndexTransfer = suspiciousAccountTransfers.get(1);
        final var zeroIndexTransfer = suspiciousAccountTransfers.get(0);

        final int oneIndexAccountTransferId = getIntFromLong(oneIndexTransfer.getAccountTransferId());
        final int zeroIndexAccountTransferId = getIntFromLong(zeroIndexTransfer.getAccountTransferId());

        mockMvc.perform(get("/suspicious/account/transfer?id=1&id=2"))
                .andExpectAll(status().isOk(),
                        jsonPath("$", hasSize(suspiciousAccountTransfers.size())),
                        jsonPath("$.[0].accountTransferId", is(zeroIndexAccountTransferId)),
                        jsonPath("$.[0].isBlocked", is(zeroIndexTransfer.getIsBlocked())),
                        jsonPath("$.[0].isSuspicious", is(zeroIndexTransfer.getIsSuspicious())),
                        jsonPath("$.[0].blockedReason", is(zeroIndexTransfer.getBlockedReason())),
                        jsonPath("$.[0].suspiciousReason", is(zeroIndexTransfer.getSuspiciousReason())),
                        jsonPath("$.[1].isBlocked", is(oneIndexTransfer.getIsBlocked())),
                        jsonPath("$.[1].accountTransferId", is(oneIndexAccountTransferId)),
                        jsonPath("$.[1].isSuspicious", is(oneIndexTransfer.getIsSuspicious())),
                        jsonPath("$.[1].blockedReason", is(oneIndexTransfer.getBlockedReason())),
                        jsonPath("$.[1].suspiciousReason", is(oneIndexTransfer.getSuspiciousReason()))
                );
    }

    @Test
    @DisplayName("чтение по списку несуществующих id, негативный сценарий")
    void readAllNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(TWO, SUSPICIOUS_ACCOUNT_TRANSFER_NAME)))
                .when(service).readAll(any());

        mockMvc.perform(
                        get("/suspicious/account/transfer?id=" + TWO))
                .andExpectAll(status().isNotFound(),
                        content().string(getNotFoundExceptionMessage(TWO, SUSPICIOUS_ACCOUNT_TRANSFER_NAME))
                );
    }

    private List<SuspiciousAccountTransferDto> readAllTestPrepare() {

        final List<SuspiciousAccountTransferDto> suspiciousAccountTransfers = List.of(
                suspiciousAccountTransferSupplier.getDto(ONE, ONE, TRUE, REASON),
                suspiciousAccountTransferSupplier.getDto(TWO, TWO, FALSE, REASON)
        );

        doReturn(suspiciousAccountTransfers).when(service).readAll(any());

        return suspiciousAccountTransfers;
    }

    @Test
    @DisplayName("чтение по некорректному списку id, негативный сценарий")
    void readAllIncorrectIdNegativeTest() throws Exception {
        doThrow(new IllegalArgumentException()).when(service).readAll(any());

        mockMvc.perform(
                        get("/suspicious/account/transfer?id=6&id=4&id=String"))
                .andExpectAll(status().isInternalServerError());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(suspiciousAccountTransfer).when(service).update(any(), anyLong());

        final int accountTransferId = getIntFromLong(suspiciousAccountTransfer.getAccountTransferId());

        mockMvc.perform(
                        put("/suspicious/account/transfer/{id}", ONE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(suspiciousAccountTransfer)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.accountTransferId", is(accountTransferId)),
                        jsonPath("$.isBlocked", is(suspiciousAccountTransfer.getIsBlocked())),
                        jsonPath("$.isSuspicious", is(suspiciousAccountTransfer.getIsSuspicious())),
                        jsonPath("$.blockedReason", is(suspiciousAccountTransfer.getBlockedReason())),
                        jsonPath("$.suspiciousReason", is(suspiciousAccountTransfer.getSuspiciousReason()))
                );
    }

    @Test
    @DisplayName("обновление несуществующего перевода, негативный сценарий")
    void updateNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(ONE, SUSPICIOUS_ACCOUNT_TRANSFER_NAME)))
                .when(service).update(any(), anyLong());

        mockMvc.perform(
                put("/suspicious/account/transfer/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousAccountTransfer))
        ).andExpectAll(status().isNotFound(),
                content().string(getNotFoundExceptionMessage(ONE, SUSPICIOUS_ACCOUNT_TRANSFER_NAME))
        );
    }

    @Test
    @DisplayName("обновление, передача pdf вместо json, негативный сценарий")
    void updateWrongMediaTypeNegativeTest() throws Exception {
        mockMvc.perform(
                        put("/suspicious/account/transfer/{id}", ONE)
                                .contentType(MediaType.APPLICATION_PDF)
                                .content(mapper.writeValueAsString(suspiciousAccountTransfer)))
                .andExpectAll(
                        status().isInternalServerError()
                );
    }
}
