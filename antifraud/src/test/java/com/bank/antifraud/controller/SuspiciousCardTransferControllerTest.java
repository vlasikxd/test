package com.bank.antifraud.controller;

import com.bank.antifraud.ParentTest;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.transferDto.CardTransferDto;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.bank.antifraud.supplier.SuspiciousCardTransferSupplier;
import com.bank.common.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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

@WebMvcTest(SuspiciousCardTransferController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SuspiciousCardTransferControllerTest extends ParentTest {

    private static SuspiciousCardTransferDto suspiciousCardTransfer;
    private static SuspiciousCardTransferSupplier suspiciousCardTransferSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mock;

    @MockBean
    private SuspiciousCardTransferService service;

    @BeforeAll
    static void init() {
        suspiciousCardTransferSupplier = new SuspiciousCardTransferSupplier();

        suspiciousCardTransfer = suspiciousCardTransferSupplier.getDto(ONE, ONE, TRUE, TRUE);
    }

    @Test
    @Disabled("Этот тест не работает на данный момент")
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(suspiciousCardTransfer).when(service).create(any());

        final CardTransferDto cardTransferId = suspiciousCardTransfer.getCardTransferId();

        mock.perform(
                post("/suspicious/card/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousCardTransfer))
        ).andExpectAll(status().isOk(),
                jsonPath("$.cardTransferId", is(cardTransferId)),
                jsonPath("$.isBlocked", is(suspiciousCardTransfer.getIsBlocked())),
                jsonPath("$.isSuspicious", is(suspiciousCardTransfer.getIsSuspicious())),
                jsonPath("$.blockedReason", is(suspiciousCardTransfer.getBlockedReason())),
                jsonPath("$.suspiciousReason", is(suspiciousCardTransfer.getSuspiciousReason()))
        );
    }

    @Test
    @DisplayName("сохранение невалидного значения, негативный сценарий")
    void createNoValidNegativeTest() throws Exception {
        final String exceptionMessage = "Дублирование значения уникального поля";

        doThrow(new ValidationException(exceptionMessage)).when(service).create(any());

        mock.perform(
                post("/suspicious/card/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousCardTransfer))
        ).andExpectAll(status().isUnprocessableEntity(),
                content().string(exceptionMessage)
        );
    }

    @Test
    @DisplayName("сохранение pdf вместо json, негативный сценарий")
    void createWrongMediaTypeNegativeTest() throws Exception {
        mock.perform(
                post("/suspicious/card/transfer")
                        .contentType(MediaType.APPLICATION_PDF)
                        .content(mapper.writeValueAsString(suspiciousCardTransfer))
        ).andExpectAll(status().isInternalServerError());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    @Disabled("Этот тест не работает на данный момент")
    void readPositiveTest() throws Exception {
        doReturn(suspiciousCardTransfer).when(service).read(any());

        final CardTransferDto cardTransferId = suspiciousCardTransfer.getCardTransferId();

        mock.perform(
                        get("/suspicious/card/transfer/{id}", ONE)).
                andExpectAll(status().isOk(),
                        jsonPath("$.cardTransferId", is(cardTransferId)),
                        jsonPath("$.isBlocked", is(suspiciousCardTransfer.getIsBlocked())),
                        jsonPath("$.isSuspicious", is(suspiciousCardTransfer.getIsSuspicious())),
                        jsonPath("$.blockedReason", is(suspiciousCardTransfer.getBlockedReason())),
                        jsonPath("$.suspiciousReason", is(suspiciousCardTransfer.getSuspiciousReason()))
                );
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(ONE, SUSPICIOUS_CARD_TRANSFER_NAME)))
                .when(service).read(any());

        mock.perform(
                get("/suspicious/card/transfer/{id}", ONE)
        ).andExpectAll(status().isNotFound(),
                content().string(getNotFoundExceptionMessage(ONE, SUSPICIOUS_CARD_TRANSFER_NAME))
        );
    }

    @Test
    @DisplayName("чтение по неверному параметру, негативный сценарий)")
    void readIncorrectParamNegativeTest() throws Exception {
        mock.perform(
                        get("/suspicious/card/transfer/{id}", ONE_AND_HALF))
                .andExpectAll(
                        status().is4xxClientError());
    }


    @Test
    @Disabled("Этот тест не работает на данный момент")
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<SuspiciousCardTransferDto> suspiciousCardTransfers = readAllTestPrepare();

        final var oneIndexTransfer = suspiciousCardTransfers.get(1);
        final var zeroIndexTransfer = suspiciousCardTransfers.get(0);

        final CardTransferDto oneIndexCardTransferId = oneIndexTransfer.getCardTransferId();
        final CardTransferDto zeroIndexCardTransferId = zeroIndexTransfer.getCardTransferId();

        mock.perform(
                        get("/suspicious/card/transfer?id=1&id=2"))
                .andExpectAll(status().isOk(),
                        jsonPath("$", hasSize(suspiciousCardTransfers.size())),
                        jsonPath("$.[0].cardTransferId", is(zeroIndexCardTransferId)),
                        jsonPath("$.[0].isBlocked", is(zeroIndexTransfer.getIsBlocked())),
                        jsonPath("$.[0].isSuspicious", is(zeroIndexTransfer.getIsSuspicious())),
                        jsonPath("$.[0].blockedReason", is(zeroIndexTransfer.getBlockedReason())),
                        jsonPath("$.[0].suspiciousReason", is(zeroIndexTransfer.getSuspiciousReason())),
                        jsonPath("$.[1].cardTransferId", is(oneIndexCardTransferId)),
                        jsonPath("$.[1].isBlocked", is(oneIndexTransfer.getIsBlocked())),
                        jsonPath("$.[1].isSuspicious", is(oneIndexTransfer.getIsSuspicious())),
                        jsonPath("$.[1].blockedReason", is(oneIndexTransfer.getBlockedReason())),
                        jsonPath("$.[1].suspiciousReason", is(oneIndexTransfer.getSuspiciousReason()))
                );
    }

    @Test
    @DisplayName("чтение по списку несуществующих id, негативный сценарий")
    void readAllNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(TWO, SUSPICIOUS_CARD_TRANSFER_NAME)))
                .when(service).readAll(any());

        mock.perform(
                        get("/suspicious/card/transfer?id=" + TWO))
                .andExpectAll(status().isNotFound(),
                        content().string(getNotFoundExceptionMessage(TWO, SUSPICIOUS_CARD_TRANSFER_NAME))
                );
    }

    private List<SuspiciousCardTransferDto> readAllTestPrepare() {
        final List<SuspiciousCardTransferDto> suspiciousCardTransfers = List.of(
                suspiciousCardTransferSupplier.getDto(ONE, ONE, TRUE, TRUE),
                suspiciousCardTransferSupplier.getDto(TWO, TWO, FALSE, TRUE)
        );

        doReturn(suspiciousCardTransfers).when(service).readAll(any());

        return suspiciousCardTransfers;
    }

    @Test
    @DisplayName("чтение по списку с некорректным id, негативный сценарий")
    void readAllWrongIdNegativeTest() throws Exception {
        doThrow(new IllegalArgumentException()).when(service).readAll(any());

        mock.perform(
                        get("/suspicious/card/transfer?id=6&id=4&id=String"))
                .andExpectAll(
                        status().is4xxClientError(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    @Disabled("Этот тест не работает на данный момент")
    void updatePositiveTest() throws Exception {
        doReturn(suspiciousCardTransfer).when(service).update(any(), anyLong());

        final CardTransferDto cardTransferId = suspiciousCardTransfer.getCardTransferId();

        mock.perform(
                put("/suspicious/card/transfer/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousCardTransfer))
        ).andExpectAll(status().isOk(),
                jsonPath("$.cardTransferId", is(cardTransferId)),
                jsonPath("$.isBlocked", is(suspiciousCardTransfer.getIsBlocked())),
                jsonPath("$.isSuspicious", is(suspiciousCardTransfer.getIsSuspicious())),
                jsonPath("$.blockedReason", is(suspiciousCardTransfer.getBlockedReason())),
                jsonPath("$.suspiciousReason", is(suspiciousCardTransfer.getSuspiciousReason()))
        );
    }

    @Test
    @DisplayName("обновление несуществующего перевода, негативный сценарий")
    void updateNoIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(getNotFoundExceptionMessage(ONE, SUSPICIOUS_CARD_TRANSFER_NAME)))
                .when(service).update(any(), anyLong());

        mock.perform(
                put("/suspicious/card/transfer/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(suspiciousCardTransfer))
        ).andExpectAll(
                status().isNotFound(),
                content().string(getNotFoundExceptionMessage(ONE, SUSPICIOUS_CARD_TRANSFER_NAME))
        );
    }

    @Test
    @DisplayName("обновление pdf вместо json, негативный сценарий")
    void updateWithWrongMediaTypeNegativeTest() throws Exception {
        mock.perform(
                put("/suspicious/card/transfer/{id}", ONE)
                        .contentType(MediaType.APPLICATION_PDF)
                        .content(mapper.writeValueAsString(suspiciousCardTransfer))
        ).andExpectAll(
                status().isInternalServerError()
        );
    }
}
