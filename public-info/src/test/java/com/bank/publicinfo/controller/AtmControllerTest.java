package com.bank.publicinfo.controller;

import com.bank.common.exception.ValidationException;
import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.service.AtmService;
import com.bank.publicinfo.supplier.AtmSupplier;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

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

@WebMvcTest(AtmController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AtmControllerTest extends ParentTest {

    private static AtmDto atm;
    private static AtmSupplier atmSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;


    @MockBean
    private AtmService service;

    @BeforeAll
    static void init() {
        atmSupplier = new AtmSupplier();

        atm = atmSupplier.getDto(ONE, SPACE, TRUE, null);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() throws Exception {
        doReturn(atm).when(service).save(any());

        mockMvc.perform(post("/atm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(atm))
        ).andExpectAll(status().isOk(),
                jsonPath("$.address", is(atm.getAddress())),
                jsonPath("$.startOfWork", is(atm.getStartOfWork().toString())),
                jsonPath("$.endOfWork", is(atm.getEndOfWork().toString())),
                jsonPath("$.allHours", is(atm.getAllHours())),
                jsonPath("$.branch", is(atm.getBranch()))
        );
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() throws Exception {
        String errorMessage = "Неверные данные";

        doThrow(new ValidationException(errorMessage)).when(service).save(any());

        mockMvc.perform(post("/atm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(atm))
        ).andExpectAll(status().isUnprocessableEntity(),
                content().string(errorMessage)
        );
    }

    @Test
    @DisplayName("Сохранение, передан pdf, негативный сценарий")
    void saveWrongMediaTypeNegativeTest() throws Exception {
        mockMvc.perform(post("/atm")
                        .contentType(MediaType.APPLICATION_PDF)
                        .content(mapper.writeValueAsString(atm))
        ).andExpectAll(status().is5xxServerError());
    }

    @Test
    @DisplayName("Чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(atm).when(service).read(any());

        final int id = getIntFromLong(atm.getId());

        mockMvc.perform(get("/atm/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.address", is(atm.getAddress())),
                        jsonPath("$.startOfWork", is(atm.getStartOfWork().toString())),
                        jsonPath("$.endOfWork", is(atm.getEndOfWork().toString())),
                        jsonPath("$.allHours", is(atm.getAllHours())),
                        jsonPath("$.branch", is(atm.getBranch()))
                );
    }

    @Test
    @DisplayName("Чтение. негативный сценарий")
    void readNegativeTest() throws Exception {
        String errorMessage = "Банкомата нет";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).read(any());

        mockMvc.perform(get("/atm/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(errorMessage)
                );
    }

    @Test
    @DisplayName("Чтение, в id передана строка, негативный сценарий")
    void readWrongIdNegativeTest() throws Exception {

        String wrongId = "test";

        mockMvc.perform(get("/atm/" + wrongId))
                .andExpectAll(status().is4xxClientError());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<AtmDto> atms = returnAtms();

        doReturn(atms).when(service).readAll(any());

        final AtmDto zeroAtm = atms.get(0);
        final AtmDto oneAtm = atms.get(1);

        final int zeroId = getIntFromLong(zeroAtm.getId());

        final int oneId = getIntFromLong(oneAtm.getId());

        mockMvc.perform(get("/atm?id=1&id=2"))
                .andExpectAll(status().isOk(),
                        jsonPath("$", hasSize(atms.size())),
                        jsonPath("$.[0].id", is(zeroId)),
                        jsonPath("$.[0].startOfWork", is(zeroAtm.getStartOfWork().format(DateTimeFormatter.ISO_TIME))),
                        jsonPath("$.[0].endOfWork", is(zeroAtm.getEndOfWork().format(DateTimeFormatter.ISO_TIME))),
                        jsonPath("$.[0].address", is(zeroAtm.getAddress())),
                        jsonPath("$.[0].allHours", is(zeroAtm.getAllHours())),
                        jsonPath("$.[0].branch", is(zeroAtm.getBranch())),
                        jsonPath("$.[1].id", is(oneId)),
                        jsonPath("$.[1].startOfWork", is(oneAtm.getStartOfWork().format(DateTimeFormatter.ISO_TIME))),
                        jsonPath("$.[1].endOfWork", is(oneAtm.getEndOfWork().format(DateTimeFormatter.ISO_TIME))),
                        jsonPath("$.[1].address", is(oneAtm.getAddress())),
                        jsonPath("$.[1].allHours", is(oneAtm.getAllHours())),
                        jsonPath("$.[1].branch", is(oneAtm.getBranch()))
                );
    }

    private List<AtmDto> returnAtms() {
        return List.of(
                atmSupplier.getDto(ONE, SPACE, TRUE, null),
                atmSupplier.getDto(ONE, SPACE, TRUE, null)
        );
    }

    @Test
    @DisplayName("Чтение по нескольким id, негативный сценарий")
    void readAllNegativeTest() throws Exception {
        String errorMessage = "Ошибка в параметрах";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).readAll(any());

        mockMvc.perform(get("/atm?id=1"))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(errorMessage)
                );
    }

    @Test
    @DisplayName("Чтение по нескольким id, в id передана строка, негативный сценарий")
    void readAllWrongIdNegativeTest() throws Exception {
        String wrongId = "test";

        mockMvc.perform(get("/atm?id=1&id=" + wrongId))
                .andExpectAll(status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(atm).when(service).update(anyLong(), any());

        final int id = getIntFromLong(atm.getId());

        mockMvc.perform(put("/atm/{id}", ONE).
                contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(atm))
        ).andExpectAll(status().isOk(),
                jsonPath("$.id", is(id)),
                jsonPath("$.address", is(atm.getAddress())),
                jsonPath("$.startOfWork", is(atm.getStartOfWork().format(DateTimeFormatter.ISO_TIME))),
                jsonPath("$.endOfWork", is(atm.getEndOfWork().format(DateTimeFormatter.ISO_TIME))),
                jsonPath("$.allHours", is(atm.getAllHours())),
                jsonPath("$.branch", is(atm.getBranch()))
        );
    }

    @Test
    @DisplayName("Обновление, негативный сценарий")
    void updateNegativeTest() throws Exception {
        String errorMessage = "Обновление невозможно";

        doThrow(new EntityNotFoundException(errorMessage)).when(service).update(anyLong(), any());

        mockMvc.perform(put("/atm/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(atm))
        ).andExpectAll(status().isNotFound(),
                content().string(errorMessage));
    }

    @Test
    @DisplayName("Обновление, в id передана строка, негативный сценарий")
    void updateWrongIdNegativeTest() throws Exception {
        String wrongId = "test";


        mockMvc.perform(put("/atm/" + wrongId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(atm))
        ).andExpectAll(status().is4xxClientError());
    }
}

