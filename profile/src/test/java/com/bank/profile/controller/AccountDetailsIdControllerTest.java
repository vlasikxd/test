package com.bank.profile.controller;

import com.bank.common.exception.ValidationException;
import com.bank.profile.ParentTest;
import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.service.AccountDetailsIdService;
import com.bank.profile.supplier.AccountDetailsIdSupplier;
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
import org.springframework.test.web.servlet.ResultActions;

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

@WebMvcTest(AccountDetailsIdController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountDetailsIdControllerTest extends ParentTest {

    private static AccountDetailsIdDto accountDetails;
    private static AccountDetailsIdSupplier accountDetailsIdSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mock;

    private ResultActions response;

    @MockBean
    private AccountDetailsIdService service;

    @BeforeAll
    static void init() {
        accountDetailsIdSupplier = new AccountDetailsIdSupplier();

        accountDetails = accountDetailsIdSupplier.getDto(ONE, TWO, null);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(accountDetails).when(service).save(any());

        response = mock.perform(post("/account/details-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails))
        );

        final int accountId = getIntFromLong(accountDetails.getAccountId());

        response.andExpectAll(
                status().isOk(),
                jsonPath("$.accountId", is(accountId)),
                jsonPath("$.profile", is(accountDetails.getProfile()))
        );
    }

    @Test
    @DisplayName("сохранение негативный сценарий")
    void saveNegativeTest() throws Exception {
        doThrow(new ValidationException("Неверные данные")).when(service).save(any());

        response = mock.perform(post("/account/details-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails))
        );

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string("Неверные данные")
        );
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() throws Exception {
        doReturn(accountDetails).when(service).read(any());

        final int accountDetailsId = getIntFromLong(accountDetails.getId());
        final int accountId = getIntFromLong(accountDetails.getAccountId());

        mock.perform(get("/account/details-id/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(accountDetailsId)),
                        jsonPath("$.accountId", is(accountId)),
                        jsonPath("$.profile", is(accountDetails.getProfile()))
                );
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Пользователя нет")).when(service).read(any());

        mock.perform(get("/account/details-id/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Пользователя нет")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id позитивный сценарий")
    void readAllTest() throws Exception {
        final List<AccountDetailsIdDto> accountDetailsIds = returnAccountDetailsIds();

        final var zeroIndexAccountDetail = accountDetailsIds.get(0);
        final var oneIndexAccountDetails = accountDetailsIds.get(1);

        final int zeroAccountDetailsId = getIntFromLong(zeroIndexAccountDetail.getId());
        final int zeroAccountId = getIntFromLong(zeroIndexAccountDetail.getAccountId());

        final int oneAccountDetailsId = getIntFromLong(oneIndexAccountDetails.getId());
        final int oneAccountId = getIntFromLong(oneIndexAccountDetails.getAccountId());

        doReturn(accountDetailsIds).when(service).readAll(any());

        mock.perform(get("/account/details-id?id=1&id=2")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(accountDetailsIds.size())),
                jsonPath("$.[0].id", is(zeroAccountDetailsId)),
                jsonPath("$.[0].accountId", is(zeroAccountId)),
                jsonPath("$.[0].profile", is(zeroIndexAccountDetail.getProfile())),
                jsonPath("$.[1].id", is(oneAccountDetailsId)),
                jsonPath("$.[1].accountId", is(oneAccountId)),
                jsonPath("$.[1].profile", is(oneIndexAccountDetails.getProfile()))
        );
    }

    private List<AccountDetailsIdDto> returnAccountDetailsIds() {
        return List.of(
                accountDetailsIdSupplier.getDto(ONE, ONE, null),
                accountDetailsIdSupplier.getDto(TWO, TWO, null)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id негативный сценарий")
    void readAllNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Ошибка в параметрах")).when(service).readAll(any());

        mock.perform(get("/account/details-id?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string("Ошибка в параметрах")
                );
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(accountDetails).when(service).update(anyLong(), any());

        response = mock.perform(put("/account/details-id/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails))
        );

        final int accountDetailsId = getIntFromLong(accountDetails.getId());
        final int accountId = getIntFromLong(accountDetails.getAccountId());

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(accountDetailsId)),
                jsonPath("$.accountId", is(accountId)),
                jsonPath("$.profile", is(accountDetails.getProfile()))
        );
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Обновление невозможно")).when(service).update(anyLong(), any());

        response = mock.perform(put("/account/details-id/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails))
        );

        response.andExpectAll(status().isNotFound(),
                content().string("Обновление невозможно")
        );
    }
}
