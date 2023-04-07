package com.bank.account.controller;

import com.bank.account.ParentTest;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsService;
import com.bank.common.exception.ValidationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.bank.account.supplier.AccountDetailsSupplier.getAccountDetailsDto;


@WebMvcTest(AccountDetailsController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountDetailsControllerTest extends ParentTest {

    private static AccountDetailsDto accountDetailsPositiveFirst;
    private static AccountDetailsDto accountDetailsPositiveSecond;

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private AccountDetailsService service;

    @BeforeAll
    static void init() {
        accountDetailsPositiveFirst =
                getAccountDetailsDto(
                        ONE, ONE, ONE, ONE, BIG_DECIMAL_THREE, Boolean.TRUE, ONE
                );

        accountDetailsPositiveSecond =
                getAccountDetailsDto(
                        TWO, TWO, TWO, TWO, BIG_DECIMAL_THREE, Boolean.TRUE, TWO
                );
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(accountDetailsPositiveFirst)
                .when(service)
                .readById(any());

        mockMvc.perform(get("/details/{id}", ONE))

                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(value(accountDetailsPositiveFirst.getId()))),
                        jsonPath("$.money", is(accountDetailsPositiveFirst.getMoney().intValue())),
                        jsonPath("$.profileId", is(value(accountDetailsPositiveFirst.getProfileId()))),
                        jsonPath("$.passportId", is(value(accountDetailsPositiveFirst.getPassportId()))),
                        jsonPath("$.negativeBalance", is(accountDetailsPositiveFirst.getNegativeBalance())),
                        jsonPath("$.accountNumber", is(value(accountDetailsPositiveFirst.getAccountNumber()))),
                        jsonPath("$.bankDetailsId", is(value(accountDetailsPositiveFirst.getBankDetailsId())))
                );
    }

    @Test
    @DisplayName("чтение несуществующего id, негативный сценарий")
    void readAbsentIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(NOT_FOUND_BODY))
                .when(service)
                .readById(any());

        mockMvc.perform(get("/details/{id}", ONE))

                .andExpectAll(status().isNotFound(),
                        content().string(NOT_FOUND_BODY)
                );
    }

    @Test
    @DisplayName("чтение некорректного id, негативный сценарий")
    void readInvalidIdNegativeTest() throws Exception {
        when(service.readById(any()))
                .thenThrow(MethodArgumentTypeMismatchException.class);

        mockMvc.perform(get("/details/${id}", TEST_STRING))

                .andExpectAll(status().isBadRequest(),
                        content().string(BAD_REQUEST_BODY_INCORRECT_ID)
                );
    }

    @Test
    @DisplayName("чтение по нескольким идентификаторам, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<AccountDetailsDto> accountDetailsDtos =
                List.of(accountDetailsPositiveFirst, accountDetailsPositiveSecond);

        when(service.readAllById(anyList()))
                .thenReturn(accountDetailsDtos);

        String jsonResponse = mockMvc
                .perform(get("/details?id={id1}&id={id2}", ONE, TWO))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(TWO.intValue()))
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        final List<AccountDetailsDto> accountDetailsResponseDtos =
                mapper.readValue(jsonResponse, new TypeReference<>() {
                });

        assertIterableEquals(accountDetailsDtos, accountDetailsResponseDtos);
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим идентификаторам, негативный сценарий")
    void readAllAbsentIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(NOT_FOUND_BODY))
                .when(service)
                .readAllById(anyList());

        mockMvc.perform(get("/details?id=1"))

                .andExpectAll(status().isNotFound(),
                        content().string(NOT_FOUND_BODY)
                );
    }

    @Test
    @DisplayName("чтение по нескольким id = null, негативный сценарий")
    void readAllNullIdNegativeTest() throws Exception {
        when(service.readAllById(anyList()))
                .thenThrow(new EntityNotFoundException(NOT_FOUND_BODY));

        mockMvc.perform(get("/details?id={id1}&id={id2}", null, null))

                .andExpectAll(status().isNotFound(),
                        content().string(NOT_FOUND_BODY)
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, не переданы параметры, негативный сценарий")
    void readAllAbsentParamsNegativeTest() throws Exception {

        when(service.readAllById(anyList()))
                .thenAnswer(invocation -> {
                    throw new MissingServletRequestPartException("id");
                });

        mockMvc.perform(get("/details"))

                .andExpectAll(status().isBadRequest(),
                        content().string(getMissingServletRequestParameterExceptionMessage("id"))
                );
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(accountDetailsPositiveFirst).when(service).create(any());

        mockMvc.perform(post("/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetailsPositiveFirst)))

                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(value(accountDetailsPositiveFirst.getId()))),
                        jsonPath("$.money", is(accountDetailsPositiveFirst.getMoney().intValue())),
                        jsonPath("$.profileId", is(value(accountDetailsPositiveFirst.getProfileId()))),
                        jsonPath("$.passportId", is(value(accountDetailsPositiveFirst.getPassportId()))),
                        jsonPath("$.negativeBalance", is(accountDetailsPositiveFirst.getNegativeBalance())),
                        jsonPath("$.accountNumber", is(value(accountDetailsPositiveFirst.getAccountNumber()))),
                        jsonPath("$.bankDetailsId", is(value(accountDetailsPositiveFirst.getBankDetailsId())))
                );
    }

    @Test
    @DisplayName("создание объекта, передача невалидной сущности, негативный сценарий")
    void createInvalidAccountDetailsEntityNegativeTest() throws Exception {
        doThrow(new ValidationException(BAD_REQUEST_BODY_INVALID_ENTITY)).when(service).create(any());

        mockMvc.perform(post("/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetailsPositiveFirst)))

                .andExpectAll(status().isUnprocessableEntity(),
                        content().string(BAD_REQUEST_BODY_INVALID_ENTITY)
                );
    }

    @Test
    @DisplayName("создание объекта, передача невалидного json, негативный сценарий")
    void createInvalidJsonNegativeTest() throws Exception {
        when(service.create(any()))
                .thenThrow(new HttpMessageNotReadableException(
                        BAD_REQUEST_BODY_INVALID_JSON,
                        any(HttpInputMessage.class))
                );

        mockMvc.perform(post("/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetailsPositiveFirst)))

                .andExpectAll(status().isBadRequest(),
                        content().string(BAD_REQUEST_BODY_INVALID_JSON)
                );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(accountDetailsPositiveFirst).when(service).update(anyLong(), any());

        mockMvc.perform(put("/details/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetailsPositiveFirst)))

                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(value(accountDetailsPositiveFirst.getId()))),
                        jsonPath("$.money", is(accountDetailsPositiveFirst.getMoney().intValue())),
                        jsonPath("$.profileId", is(value(accountDetailsPositiveFirst.getProfileId()))),
                        jsonPath("$.passportId", is(value(accountDetailsPositiveFirst.getPassportId()))),
                        jsonPath("$.negativeBalance", is(accountDetailsPositiveFirst.getNegativeBalance())),
                        jsonPath("$.accountNumber", is(value(accountDetailsPositiveFirst.getAccountNumber()))),
                        jsonPath("$.bankDetailsId", is(value(accountDetailsPositiveFirst.getBankDetailsId())))
                );
    }

    @Test
    @DisplayName("обновление несуществующего id, негативный сценарий")
    void updateAbsentIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException(NOT_FOUND_BODY))
                .when(service)
                .update(anyLong(), any());

        mockMvc.perform(put("/details/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetailsPositiveFirst)))

                .andExpectAll(status().isNotFound(),
                        content().string(NOT_FOUND_BODY)
                );
    }

    @Test
    @DisplayName("обновление, передача невалидного id, негативный сценарий")
    void updateInvalidIdNegativeTest() throws Exception {
        when(service.update(any(), any()))
                .thenThrow(MethodArgumentTypeMismatchException.class);

        mockMvc.perform(put("/details/{id}", TEST_STRING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetailsPositiveFirst)))

                .andExpectAll(status().isBadRequest(),
                        content().string(BAD_REQUEST_BODY_INCORRECT_ID));
    }

    private Integer value(Long value) {
        return value.intValue();
    }
}
