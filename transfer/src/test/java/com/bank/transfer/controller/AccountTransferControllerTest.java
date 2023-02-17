package com.bank.transfer.controller;

import com.bank.common.exception.ValidationException;
import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.returner.EntityNotFoundReturner;
import com.bank.transfer.service.impl.AccountTransferServiceImpl;
import com.bank.transfer.validator.ReadAllValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.asm.Advice;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


@WebMvcTest(AccountTransferController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountTransferControllerTest {

    final ObjectMapper mapper;

    final MockMvc mock;
    @MockBean
    final AccountTransferServiceImpl service;

    //final EntityNotFoundReturner returner;

    static AccountTransferDto transferDto;
    static AccountTransferEntity transfer;
    static AccountTransferEntity transferEntity;
    static final Long idf = 14L;
    static List<Long> ids;
    static List<AccountTransferDto> transferList;



    @BeforeAll
    public static void init(){

        transferDto = new AccountTransferDto(14L,14L, BigDecimal.valueOf(14)
                ,"Ramzan", 14L);
        transfer = new AccountTransferEntity(15L,15L,15L,BigDecimal.valueOf(15)
                ,"Tarzan");
        transferEntity = new AccountTransferEntity(14L, 14L, 14L, BigDecimal.valueOf(14)
                ,"Ramzan");
        ids = List.of(idf);
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
                .andExpectAll();
    }

    @Test
    @DisplayName("Позитивный сценарий чтения транзакции")
    void readTest() throws Exception {
        when(service.read(any())).thenReturn(transferDto);
        mock.perform(get(String.format("/account/read?id=%s",idf)))
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
        mock.perform(get(String.format("/account/read?id=%s",idf))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(idf)))
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
        String massage = "Ошибка в переданных параметрах";

        doThrow(new EntityNotFoundException(massage)).when(service).readAll(any());

        mock.perform(get("/account/read/all?ids=14"))
                .andExpectAll(status().isNotFound(),
                        content().string(massage)
                );
    }

    @Test
    @DisplayName("Позитивный сценарий обновления транзакции")
    void updateTest() throws Exception {
        when(service.update(ArgumentMatchers.any(), anyLong())).thenReturn(transferDto);
        mock.perform(put(String.format("/account/update?id=%s", idf))
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
        mock.perform(put(String.format("/account/update?id=%s", idf))
                .content(mapper.writeValueAsString(transferDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound(), content()
                        .string("AccountTransfer для обновления с указанным id не найден"));
    }
}