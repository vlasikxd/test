package com.bank.account.controller;

import com.bank.account.ParentTest;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsService;
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
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityNotFoundException;

import java.util.Collections;

import static com.bank.account.ParentTest.AccountDetailsSupplier.getAccountDetailsDto;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
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

@WebMvcTest(AccountDetailsController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountDetailsControllerTest extends ParentTest {

    private static AccountDetailsDto accountDetails;

    private final ObjectMapper mapper;
    private final MockMvc mock;

    private ResultActions response;

    @MockBean
    private AccountDetailsService service;

    @BeforeAll
    static void init() {
        accountDetails = getAccountDetailsDto(ONE, ONE, ONE, ONE, BIG_DECIMAL_THREE, Boolean.TRUE, ONE);
    }

    //shouldReadAccountById
    @Test
    @DisplayName("Чтение: позитивный сценарий")
    void readTest() throws Exception {
        doReturn(accountDetails).when(service).readById(any());

        mock.perform(get("/details/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(value(accountDetails.getId()))),
                        jsonPath("$.money", is(accountDetails.getMoney().intValue())),
                        jsonPath("$.profileId", is(value(accountDetails.getProfileId()))),
                        jsonPath("$.passportId", is(value(accountDetails.getPassportId()))),
                        jsonPath("$.negativeBalance", is(accountDetails.getNegativeBalance())),
                        jsonPath("$.accountNumber", is(value(accountDetails.getAccountNumber()))),
                        jsonPath("$.bankDetailsId", is(value(accountDetails.getBankDetailsId())))
                );
    }

    @Test
    @DisplayName("Чтение: негативный сценарий 1")
    void readNegativeTest1() throws Exception {
        String message = "Пользователя с таким id нет";

        doThrow(new EntityNotFoundException(message)).when(service).readById(any());

        mock.perform(get("/details/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(message)
                );
    }


    @Test
    @DisplayName("Чтение: негативный сценарий 2")
    void readNegativeTest2() throws Exception {

       String message = "Некорректно указан id";

       doThrow(new EntityNotFoundException(message)).when(service).readById(any());

        mock.perform(get("/details/{id}", "foo"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string(message)
                        );
    }

    @Test
    @DisplayName("Чтение: негативный сценарий 3 (id=null)")
    void readNegativeTest3() throws Exception {
        String message = "Ошибка на стороне сервера.";

        mock.perform(get("/details/{id}", (String) null))
                .andExpectAll(
                        status().isInternalServerError(),
                        content().string(message)
                );
    }

    @Test
    @DisplayName("Чтение по нескольким идентификаторам: позитивный сценарий")
    void readAllTest() throws Exception {
        doReturn(Collections.singletonList(accountDetails)).when(service).readAllById(any());

        mock.perform(get("/details?id=1")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(1)),
                jsonPath("$.[0].id", is(value(accountDetails.getId()))),
                jsonPath("$.[0].money", is(accountDetails.getMoney().intValue())),
                jsonPath("$.[0].profileId", is(value(accountDetails.getProfileId()))),
                jsonPath("$.[0].passportId", is(value(accountDetails.getPassportId()))),
                jsonPath("$.[0].negativeBalance", is(accountDetails.getNegativeBalance())),
                jsonPath("$.[0].accountNumber", is(value(accountDetails.getAccountNumber()))),
                jsonPath("$.[0].bankDetailsId", is(value(accountDetails.getBankDetailsId())))
        );
    }

    @Test
    @DisplayName("Чтение по нескольким идентификаторам: негативный сценарий 1")
    void readAllNegativeTest1() throws Exception {
        String massage = "Ошибка в переданных параметрах";

        doThrow(new EntityNotFoundException(massage)).when(service).readAllById(any());

        mock.perform(get("/details?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string(massage)
                );
    }

    @Test
    @DisplayName("Чтение по нескольким идентификаторам: негативный сценарий 2")
    void readAllNegativeTest2() throws Exception {

        String message = "Некорректно указан id";

        doThrow(new EntityNotFoundException(message)).when(service).readById(any());

        mock.perform(get("/details/{id}", "foo"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string(message)
                );
    }

    @Test
    @DisplayName("Чтение по нескольким идентификаторам: негативный сценарий 3 (id=null)")
    void readAllNegativeTest3() throws Exception {

        String message = "Идентификатор не может быть null";

        doThrow(new IllegalArgumentException(message)).when(service).readAllById(any());

        mock.perform(get("/details?id="))
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        content().string(message)
                );
    }

    @Test
    @DisplayName("Создание: позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(accountDetails).when(service).create(any());

        response = mock.perform(post("/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails)));

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(value(accountDetails.getId()))),
                jsonPath("$.money", is(accountDetails.getMoney().intValue())),
                jsonPath("$.profileId", is(value(accountDetails.getProfileId()))),
                jsonPath("$.passportId", is(value(accountDetails.getPassportId()))),
                jsonPath("$.negativeBalance", is(accountDetails.getNegativeBalance())),
                jsonPath("$.accountNumber", is(value(accountDetails.getAccountNumber()))),
                jsonPath("$.bankDetailsId", is(value(accountDetails.getBankDetailsId())))
        );
    }
//todo переименовать названия негативных тестов в едином стиле
    @Test
    @DisplayName("Создание негативный сценарий 1")
    void saveNegativeTest1() throws Exception {
        String message = "Неверные данные";

        doThrow(new ValidationException(message)).when(service).create(any());

        response = mock.perform(post("/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails)));

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string(message)
        );
    }

    @Test
    @DisplayName("Создание негативный сценарий 2")
    void saveNegativeTest2() throws Exception {
        String message = "Неверный http-запрос";
        String json = "";

        doThrow(new ValidationException(message)).when(service).create(any());

        response = mock.perform(post("/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        response.andExpectAll(status().isBadRequest(),
                content().string(containsString("Required request body is missing")));


    }


    private Integer value(Long value) {
        return value.intValue();
    }

    @Test
    @DisplayName("Обновление: позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(accountDetails).when(service).update(anyLong(), any());

        response = mock.perform(put("/details/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails)));

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(value(accountDetails.getId()))),
                jsonPath("$.money", is(accountDetails.getMoney().intValue())),
                jsonPath("$.profileId", is(value(accountDetails.getProfileId()))),
                jsonPath("$.passportId", is(value(accountDetails.getPassportId()))),
                jsonPath("$.negativeBalance", is(accountDetails.getNegativeBalance())),
                jsonPath("$.accountNumber", is(value(accountDetails.getAccountNumber()))),
                jsonPath("$.bankDetailsId", is(value(accountDetails.getBankDetailsId())))
        );
    }

    @Test
    @DisplayName("Обновление: негативный сценарий 1")
    void updateNegativeTest1() throws Exception {
        String massage = "Обновление невозможно";

        doThrow(new EntityNotFoundException(massage)).when(service).update(anyLong(), any());

        response = mock.perform(put("/details/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails)));

        response.andExpectAll(status().isNotFound(),
                content().string(massage)
        );
    }

    @Test
    @DisplayName("Обновление: негативный сценарий 2")
    void updateNegativeTest2() throws Exception {
        String message = "Запись не найдена";

        doThrow(new EntityNotFoundException(message)).when(service).update(anyLong(), any());

        response = mock.perform(put("/details/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails)));

        response.andExpectAll(status().isNotFound(),
                content().string(message)
        );
    }

    @Test
    @DisplayName("Обновление: негативный сценарий 3 (id=null)")
    void updateNegativeTest3() throws Exception {
        String massage = "Ошибка на стороне сервера.";

        response = mock.perform(put("/details/{id}", (Long) null)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDetails)));

        response.andExpectAll(status().isInternalServerError(),
                content().string(massage)
        );
    }



}
