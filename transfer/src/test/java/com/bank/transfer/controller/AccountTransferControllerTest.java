package com.bank.transfer.controller;

import com.bank.common.exception.ValidationException;
import com.bank.transfer.ParentTest;
import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.service.impl.AccountTransferServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc()
@WebMvcTest(AccountTransferController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AccountTransferControllerTest extends ParentTest {

    private static AccountTransferDto transferDto;

    private static List<Long> ids;

    private static List<AccountTransferDto> transferList;

    private final ObjectMapper mapper;

    private final MockMvc mock;
    @MockBean
    private final AccountTransferServiceImpl service;

    @BeforeAll
    public static void init(){

        transferDto = new AccountTransferDto(ID, ENTITY_NUMBER, AMOUNT, PURPOSE, ENTITY_DETAILS_ID);

        ids = List.of(ID);

        transferList = List.of(transferDto);
    }

    @Test
    @DisplayName("Позитивный сценарий добавления транзакции")
    void createTest() throws Exception {

       when(service.create(any())).thenReturn(transferDto);

       mock.perform(post("/account/create").content(mapper.writeValueAsString(transferDto))
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpectAll(jsonPath("$.id").value(transferDto.getId())
                            ,jsonPath("$.accountNumber").value(transferDto.getAccountNumber())
                            ,jsonPath("$.accountDetailsId").value(transferDto.getAccountDetailsId())
                            ,jsonPath("$.amount").value(transferDto.getAmount())
                            ,jsonPath("$.purpose").value(transferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий добавления транзакции")
    void createNegativeTest() throws Exception {

        when(service.create(any())).thenThrow(new ValidationException("Неверные данные"));

        mock.perform(post("/account/create")
                .content(mapper.writeValueAsString(transferDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Позитивный сценарий чтения транзакции")
    void readTest() throws Exception {

        when(service.read(any())).thenReturn(transferDto);

        mock.perform(get(String.format("/account/read?id=%s",ID)))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$.id").value(transferDto.getId())
                        ,jsonPath("$.accountNumber").value(transferDto.getAccountNumber())
                        ,jsonPath("$.accountDetailsId").value(transferDto.getAccountDetailsId())
                        ,jsonPath("$.amount").value(transferDto.getAmount())
                        ,jsonPath("$.purpose").value(transferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий чтения транзакции")
    void readNegativeTest() throws Exception {

        when(service.read(anyLong())).thenThrow(
                new EntityNotFoundException("AccountTransfer с указанным id не найден"));

        mock.perform(get(String.format("/account/read?id=%s",ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ID)))
                .andExpectAll(status().isNotFound());
    }

    @Test
    @DisplayName("Позитивный сценарий чтения коллекции транзакций")
    void readAllTest() throws Exception {

        when(service.readAll(ArgumentMatchers.any())).thenReturn(transferList);

        mock.perform(get(String.format("/account/read/all?ids=14")))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$", hasSize(1))
                        ,jsonPath("$.[0].id").value(transferList.get(0).getId())
                        ,jsonPath("$.[0].accountNumber").value(transferList.get(0).getAccountNumber())
                        ,jsonPath("$.[0].accountDetailsId").value(transferList.get(0).getAccountDetailsId())
                        ,jsonPath("$.[0].amount").value(transferList.get(0).getAmount())
                        ,jsonPath("$.[0].purpose").value(transferList.get(0).getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий чтения коллекции транзакции")
    public void readAllNegativeTest() throws Exception {

        final String massage = "Ошибка в переданных параметрах";

        doThrow(new EntityNotFoundException(massage)).when(service).readAll(any());

        mock.perform(get("/account/read/all?ids=14"))
                .andExpectAll(status().isNotFound(),
                        content().string(massage));
    }

    @Test
    @DisplayName("Позитивный сценарий обновления транзакции")
    void updateTest() throws Exception {

        when(service.update(ArgumentMatchers.any(), anyLong())).thenReturn(transferDto);

        mock.perform(put(String.format("/account/update?id=%s", ID))
                        .content(mapper.writeValueAsString(transferDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$.id").value(transferDto.getId())
                        ,jsonPath("$.accountNumber").value(transferDto.getAccountNumber())
                        ,jsonPath("$.accountDetailsId").value(transferDto.getAccountDetailsId())
                        ,jsonPath("$.amount").value(transferDto.getAmount())
                        ,jsonPath("$.purpose").value(transferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий обновления транзакции")
    void updateNegativeTest() throws Exception {

        when(service.update(ArgumentMatchers.any(), anyLong())).thenThrow(

                new EntityNotFoundException("AccountTransfer для обновления с указанным id не найден"));

        mock.perform(put(String.format("/account/update?id=%s", ID))
                .content(mapper.writeValueAsString(transferDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound(), content()
                        .string("AccountTransfer для обновления с указанным id не найден"));
    }
}