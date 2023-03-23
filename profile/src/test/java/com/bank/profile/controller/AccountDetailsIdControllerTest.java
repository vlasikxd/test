package com.bank.profile.controller;

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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AccountDetailsIdController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountDetailsIdControllerTest extends ParentTest {

    private static AccountDetailsIdDto accountDetails;
    private static AccountDetailsIdSupplier accountDetailsIdSupplier;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private AccountDetailsIdService service;

    @BeforeAll
    static void init() {
        accountDetailsIdSupplier = new AccountDetailsIdSupplier();

        accountDetails = accountDetailsIdSupplier.getDto(ONE, TWO, null);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(accountDetails).when(service).save(any());

        final int accountId = getIntFromLong(accountDetails.getAccountId());

        mockMvc.perform(post("/account/details-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetails)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.accountId", is(accountId)),
                        jsonPath("$.profile", is(accountDetails.getProfile()))
                );
    }

    @Test
    @DisplayName("сохранение неверных данных, негативный сценарий")
    void createIncorrectDataNegativeTest() throws Exception {
        final String exceptionMessage = "Неверные данные";

        doThrow(new EntityNotFoundException(exceptionMessage)).when(service).save(any());

        mockMvc.perform(post("/account/details-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetails)))
                .andExpectAll(status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("сохранение некорректного json, негативный сценарий")
    void createIncorrectJsonNegativeTest() throws Exception {
        final String exceptionMessage = "Некорректный json";

        doThrow(new HttpMessageNotReadableException(exceptionMessage)).when(service).save(any());

        mockMvc.perform(post("/account/details-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(accountDetails).when(service).read(any());

        mockMvc.perform(get("/account/details-id/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.profile", is(accountDetails.getProfile())),
                        jsonPath("$.id", is(getIntFromLong(accountDetails.getId()))),
                        jsonPath("$.accountId", is(getIntFromLong(accountDetails.getAccountId())))
                );
    }

    @Test
    @DisplayName("чтение несуществующего id, негативный сценарий")
    void readNotExistIdNegativeTest() throws Exception {
        final String exceptionMessage = "accountDetailsId с данным идентификатором не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage)).when(service).read(any());

        mockMvc.perform(get("/account/details-id/{id}", ONE))
                .andExpectAll(status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по некорректному id, негативный сценарий")
    void readIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).read(any());

        mockMvc.perform(get("/account/details-id/{id}", "id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<AccountDetailsIdDto> accountDetailsIds = returnAccountDetailsIds();

        doReturn(accountDetailsIds).when(service).readAll(any());

        final var zeroIndexAccountDetail = accountDetailsIds.get(0);
        final var oneIndexAccountDetails = accountDetailsIds.get(1);

        final int zeroAccountDetailsId = getIntFromLong(zeroIndexAccountDetail.getId());
        final int zeroAccountId = getIntFromLong(zeroIndexAccountDetail.getAccountId());

        final int oneAccountDetailsId = getIntFromLong(oneIndexAccountDetails.getId());
        final int oneAccountId = getIntFromLong(oneIndexAccountDetails.getAccountId());

        mockMvc.perform(get("/account/details-id?id=1&id=2"))
                .andExpectAll(status().isOk(),
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
    @DisplayName("чтение по нескольким id, негативный сценарий")
    void readAllNoUserNegativeTest() throws Exception {
        final String exceptionMessage = "Ошибка в переданных параметрах, пользователи(ь) не найден(ы)";

        doThrow(new EntityNotFoundException(exceptionMessage))
                .when(service).readAll(any());

        mockMvc.perform(get("/account/details-id?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по нескольким некорректным id, негативный сценарий")
    void readAllIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).readAll(any());

        mockMvc.perform(get("/account/details-id?id=di&id=id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(accountDetails).when(service).update(anyLong(), any());

        final int accountDetailsId = getIntFromLong(accountDetails.getId());
        final int accountId = getIntFromLong(accountDetails.getAccountId());

        mockMvc.perform(put("/account/details-id/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetails)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(accountDetailsId)),
                        jsonPath("$.accountId", is(accountId)),
                        jsonPath("$.profile", is(accountDetails.getProfile()))
                );
    }

    @Test
    @DisplayName("обновление несуществующего accountDetailsId, негативный сценарий")
    void updateNoExistAccountDetailsIdNegativeTest() throws Exception {
        final String exceptionMessage = "Обновление невозможно, пользователь не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage)).when(service).update(anyLong(), any());

        mockMvc.perform(put("/account/details-id/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetails)))
                .andExpectAll(status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("обновление некорректного json, негативный сценарий")
    void updateIncorrectJsonNegativeTest() throws Exception {
        final String exceptionMessage = "Некорректный json";

        doThrow(new HttpMessageNotReadableException(exceptionMessage)).when(service).update(anyLong(), any());

        mockMvc.perform(put("/account/details-id/{id}", TWO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }
}
