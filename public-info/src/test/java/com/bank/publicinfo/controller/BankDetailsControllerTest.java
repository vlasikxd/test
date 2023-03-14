package com.bank.publicinfo.controller;

import com.bank.common.exception.ValidationException;
import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
import com.bank.publicinfo.supplier.BankDetailsSupplier;
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

// TODO починить тест и привести к виду AtmControllerTest
@Disabled
@WebMvcTest(BankDetailsController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BankDetailsControllerTest extends ParentTest {

    private static BankDetailsDto bankDetails;
    private static BankDetailsSupplier bankDetailsSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mock;

    private ResultActions response;

    @MockBean
    private BankDetailsService service;

    @BeforeAll
    static void init() {
        bankDetailsSupplier = new BankDetailsSupplier();

        bankDetails = bankDetailsSupplier.getDto(ONE, THREE, THREE, THREE, INT_ONE, SPACE, SPACE, SPACE);
    }

    @Test
    @DisplayName("Сохранение позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(bankDetails).when(service).save(any());

        response = mock.perform(post("/bank_details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bankDetails))
        );

        final int id = getIntFromLong(bankDetails.getId());
        final int bik = getIntFromLong(bankDetails.getBik());
        final int inn = getIntFromLong(bankDetails.getInn());
        final int kpp = getIntFromLong(bankDetails.getKpp());

        response.andExpectAll(
                status().isOk(),
                jsonPath("$.id", is(id)),
                jsonPath("$.bik", is(bik)),
                jsonPath("$.inn", is(inn)),
                jsonPath("$.kpp", is(kpp)),
                jsonPath("$.corAccount", is(bankDetails.getCorAccount())),
                jsonPath("$.city", is(bankDetails.getCity())),
                jsonPath("$.joinStockCompany", is(bankDetails.getJointStockCompany())),
                jsonPath("$.name", is(bankDetails.getName()))
        );

    }

    @Test
    @DisplayName("Негативный сценарий")
    void saveNegativeTest() throws Exception {
        doThrow(new ValidationException("Неверные данные")).when(service).save(any());

        response = mock.perform(post("/bank_details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bankDetails)));

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string("Неверные данные"));
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() throws Exception {
        doReturn(bankDetails).when(service).read(any());

        final int id = getIntFromLong(bankDetails.getId());
        final int bik = getIntFromLong(bankDetails.getBik());
        final int inn = getIntFromLong(bankDetails.getInn());
        final int kpp = getIntFromLong(bankDetails.getKpp());

        mock.perform(get("/bank_details/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.bik", is(bik)),
                        jsonPath("$.inn", is(inn)),
                        jsonPath("$.kpp", is(kpp)),
                        jsonPath("$.corAccount", is(bankDetails.getCorAccount())),
                        jsonPath("$.city", is(bankDetails.getCity())),
                        jsonPath("$.joinStockCompany", is(bankDetails.getJointStockCompany())),
                        jsonPath("$.name", is(bankDetails.getName()))
                );
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Информации нет")).when(service).read(any());

        mock.perform(get("/bank_details/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Информации нет")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id позитивный сценарий")
    void readAllTest() throws Exception {
        final List<BankDetailsDto> bankDetailsList = returnBankDetailsList();

        final var zeroBankDetails = bankDetailsList.get(0);
        final var oneBankDetails = bankDetailsList.get(1);

        final int zeroId = getIntFromLong(zeroBankDetails.getId());
        final int zeroBik = getIntFromLong(zeroBankDetails.getBik());
        final int zeroInn = getIntFromLong(zeroBankDetails.getInn());
        final int zeroKpp = getIntFromLong(zeroBankDetails.getKpp());

        final int oneId = getIntFromLong(oneBankDetails.getId());
        final int oneBik = getIntFromLong(oneBankDetails.getBik());
        final int oneInn = getIntFromLong(oneBankDetails.getInn());
        final int oneKpp = getIntFromLong(oneBankDetails.getKpp());

        doReturn(bankDetailsList).when(service).readAll(any());

        mock.perform(get("/bank_details?id=1&id=2")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(bankDetailsList.size())),
                jsonPath("$.[0].id", is(zeroId)),
                jsonPath("$.[0].bik", is(zeroBik)),
                jsonPath("$.[0].inn", is(zeroInn)),
                jsonPath("$.[0].kpp", is(zeroKpp)),
                jsonPath("$.[0].corAccount", is(zeroBankDetails.getCorAccount())),
                jsonPath("$.[0].city", is(zeroBankDetails.getCity())),
                jsonPath("$.[0].joinStockCompany", is(zeroBankDetails.getJointStockCompany())),
                jsonPath("$.[0].name", is(zeroBankDetails.getName())),
                jsonPath("$.[1].id", is(oneId)),
                jsonPath("$.[1].bik", is(oneBik)),
                jsonPath("$.[1].inn", is(oneInn)),
                jsonPath("$.[1].kpp", is(oneKpp)),
                jsonPath("$.[1].corAccount", is(oneBankDetails.getCorAccount())),
                jsonPath("$.[1].city", is(oneBankDetails.getCity())),
                jsonPath("$.[1].joinStockCompany", is(oneBankDetails.getJointStockCompany())),
                jsonPath("$.[1].name", is(oneBankDetails.getName()))
        );
    }

    private List<BankDetailsDto> returnBankDetailsList() {
        return List.of(
                bankDetailsSupplier.getDto(ONE, THREE, THREE, THREE, INT_ONE, SPACE, SPACE, SPACE),
                bankDetailsSupplier.getDto(ONE, THREE, THREE, THREE, INT_ONE, SPACE, SPACE, SPACE)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id негативный сценарий")
    void readAllNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Ошибка в параметрах")).when(service).readAll(any());

        mock.perform(get("/bank_details?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string("Ошибка в параметрах")
                );
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(bankDetails).when(service).update(anyLong(), any());

        response = mock.perform(put("/bank_details/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bankDetails))
        );

        final int id = getIntFromLong(bankDetails.getId());
        final int bik = getIntFromLong(bankDetails.getBik());
        final int inn = getIntFromLong(bankDetails.getInn());
        final int kpp = getIntFromLong(bankDetails.getKpp());

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(id)),
                jsonPath("$.bik", is(bik)),
                jsonPath("$.inn", is(inn)),
                jsonPath("$.kpp", is(kpp)),
                jsonPath("$.corAccount", is(bankDetails.getCorAccount())),
                jsonPath("$.city", is(bankDetails.getCity())),
                jsonPath("$.joinStockCompany", is(bankDetails.getJointStockCompany())),
                jsonPath("$.name", is(bankDetails.getName()))
        );
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Обновление невозможно")).when(service).update(anyLong(), any());

        response = mock.perform(put("/bank_details/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bankDetails))
        );

        response.andExpectAll(status().isNotFound(),
                content().string("Обновление невозможно")
        );
    }
}
