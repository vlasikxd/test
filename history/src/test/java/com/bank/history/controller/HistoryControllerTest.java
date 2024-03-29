package com.bank.history.controller;

import com.bank.common.exception.ValidationException;
import com.bank.history.ParentTest;
import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;

import java.util.Collections;

import static com.bank.history.supplier.HistorySupplier.getHistoryDto;
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

@WebMvcTest(HistoryController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HistoryControllerTest extends ParentTest {
    private final MockMvc mock;
    private ResultActions response;
    @MockBean
    private final HistoryService service;
    private static HistoryDto history;
    private final ObjectMapper mapper;

    @BeforeAll
    static void init() {
        history = getHistoryDto(ONE, ONE, ONE, ONE, TWO, THREE, ONE);
    }

    private Integer value(Long value) {
        return value.intValue();
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(history).when(service).readById(any());

        mock.perform(get("/api/history/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(value(history.getId()))),
                        jsonPath("$.transferAuditId", is(value(history.getTransferAuditId()))),
                        jsonPath("$.profileAuditId", is(value(history.getProfileAuditId()))),
                        jsonPath("$.accountAuditId", is(value(history.getAccountAuditId()))),
                        jsonPath("$.antiFraudAuditId", is(value(history.getAntiFraudAuditId()))),
                        jsonPath("$.publicBankInfoAuditId", is(value(history.getPublicBankInfoAuditId()))),
                        jsonPath("$.authorizationAuditId", is(value(history.getAuthorizationAuditId())))

                );
    }


    @Test
    @DisplayName("чтение несуществующего id, негативный сценарий")
    void readNotExistIdNegativeTest() throws Exception {
        final String massage = "История не найдена";

        doThrow(new EntityNotFoundException(massage)).when(service).readById(any());

        mock.perform(get("/api/history/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(massage)
                );
    }

    @Test
    @DisplayName("чтение по некорректному id, негативный сценарий")
    void readIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class)
                .when(service)
                .readById(any());

        mock.perform(get("/api/history/{id}", "NotANumber"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        doReturn(Collections.singletonList(history)).when(service).readAllById(any());

        mock.perform(get("/api/history?id=1")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(1)),
                jsonPath("$.[0].id", is(value(history.getId()))),
                jsonPath("$.[0].transferAuditId", is(value(history.getTransferAuditId()))),
                jsonPath("$.[0].profileAuditId", is(value(history.getProfileAuditId()))),
                jsonPath("$.[0].accountAuditId", is(value(history.getAccountAuditId()))),
                jsonPath("$.[0].antiFraudAuditId", is(value(history.getAntiFraudAuditId()))),
                jsonPath("$.[0].publicBankInfoAuditId", is(value(history.getPublicBankInfoAuditId()))),
                jsonPath("$.[0].authorizationAuditId", is(value(history.getAuthorizationAuditId())))
        );
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNotExistIdsNegativeTest() throws Exception {
        final String massage = "Ошибка в переданных параметрах";

        doThrow(new EntityNotFoundException(massage)).when(service).readAllById(any());

        mock.perform(get("/api/history?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string(massage)
                );
    }

    @Test
    @DisplayName("чтение по нескольким некорректным id, негативный сценарий")
    void readAllIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class)
                .when(service)
                .readAllById(any());

        mock.perform(get("/api/history?id=NitANumber"))
                .andExpectAll(status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void savePositiveTest() throws Exception {
        doReturn(history).when(service).create(any());

        response = mock.perform(post("/api/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(history)));

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(value(history.getId()))),
                jsonPath("$.transferAuditId", is(value(history.getTransferAuditId()))),
                jsonPath("$.profileAuditId", is(value(history.getProfileAuditId()))),
                jsonPath("$.accountAuditId", is(value(history.getAccountAuditId()))),
                jsonPath("$.antiFraudAuditId", is(value(history.getAntiFraudAuditId()))),
                jsonPath("$.publicBankInfoAuditId", is(value(history.getPublicBankInfoAuditId()))),
                jsonPath("$.authorizationAuditId", is(value(history.getAuthorizationAuditId())))
        );
    }

    @Test
    @DisplayName("создание, некорректные данные, негативный сценарий")
    void saveIncorrectDataNegativeTest() throws Exception {
        final String massage = "Неверные данные";

        doThrow(new ValidationException(massage)).when(service).create(any());

        response = mock.perform(post("/api/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(history)));

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string(massage)
        );
    }

    @Test
    @DisplayName("создание некорректного json, негативный сценарий")
    void saveIncorrectJsonNegativeTest() throws Exception {
        final String message = "Обновление невозможно, некорректные данные!";

        doThrow(new HttpMessageNotReadableException(
                message, new MockClientHttpResponse(
                        new byte[]{}, HttpStatus.MULTI_STATUS))).when(service).create(any()
        );

        response = mock.perform(post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(message));
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(history).when(service).update(anyLong(), any());

        response = mock.perform(put("/api/history/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(history)));

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(value(history.getId()))),
                jsonPath("$.transferAuditId", is(value(history.getTransferAuditId()))),
                jsonPath("$.profileAuditId", is(value(history.getProfileAuditId()))),
                jsonPath("$.accountAuditId", is(value(history.getAccountAuditId()))),
                jsonPath("$.antiFraudAuditId", is(value(history.getAntiFraudAuditId()))),
                jsonPath("$.publicBankInfoAuditId", is(value(history.getPublicBankInfoAuditId()))),
                jsonPath("$.authorizationAuditId", is(value(history.getAuthorizationAuditId())))
        );
    }

    @Test
    @DisplayName("обновление несуществующей сущности, негативный сценарий")
    void updateNotExistEntityNegativeTest() throws Exception {
        final String massage = "Обновление невозможно";

        doThrow(new EntityNotFoundException(massage)).when(service).update(anyLong(), any());

        response = mock.perform(put("/api/history/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(history)));

        response.andExpectAll(status().isNotFound(),
                content().string(massage)
        );
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("обновление, некорректный json, негативный сценарий")
    void updateIncorrectJsonNegativeTest() throws Exception {
        final String message = "Обновление невозможно, некорректные данные!";

        doThrow(new HttpMessageNotReadableException(message)).when(service).update(anyLong(), any());

        response = mock.perform(put("/api/history/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(message)
                );
    }
}
