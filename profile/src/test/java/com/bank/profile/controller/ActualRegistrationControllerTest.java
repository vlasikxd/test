package com.bank.profile.controller;

import com.bank.common.exception.ValidationException;
import com.bank.profile.ParentTest;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.service.ActualRegistrationService;
import com.bank.profile.supplier.ActualRegistrationSupplier;
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

@WebMvcTest(ActualRegistrationController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ActualRegistrationControllerTest extends ParentTest {

    private static ActualRegistrationDto actualRegistration;
    private static ActualRegistrationSupplier actualRegistrationSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mock;

    private ResultActions response;

    @MockBean
    private ActualRegistrationService service;

    @BeforeAll
    static void init() {
        actualRegistrationSupplier = new ActualRegistrationSupplier();

        actualRegistration = actualRegistrationSupplier.getDto(
                ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, TWO
        );
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(actualRegistration).when(service).save(any());

        response = mock.perform(post("/actual_registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(actualRegistration))
        );

        final int actualRegistrationIndex = getIntFromLong(actualRegistration.getIndex());

        response.andExpectAll(
                status().isOk(),
                jsonPath("$.index", is(actualRegistrationIndex)),
                jsonPath("$.city", is(actualRegistration.getCity())),
                jsonPath("$.region", is(actualRegistration.getRegion())),
                jsonPath("$.street", is(actualRegistration.getStreet())),
                jsonPath("$.country", is(actualRegistration.getCountry())),
                jsonPath("$.district", is(actualRegistration.getDistrict())),
                jsonPath("$.locality", is(actualRegistration.getLocality())),
                jsonPath("$.houseBlock", is(actualRegistration.getHouseBlock())),
                jsonPath("$.flatNumber", is(actualRegistration.getFlatNumber())),
                jsonPath("$.houseNumber", is(actualRegistration.getHouseNumber()))
        );
    }

    @Test
    @DisplayName("сохранение негативный сценарий")
    void saveNegativeTest() throws Exception {
        doThrow(new ValidationException("Неверные данные")).when(service).save(any());

        response = mock.perform(post("/actual_registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(actualRegistration))
        );

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string("Неверные данные")
        );
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() throws Exception {
        doReturn(actualRegistration).when(service).read(any());

        final int actualRegistrationId = getIntFromLong(actualRegistration.getId());
        final int actualRegistrationIndex = getIntFromLong(actualRegistration.getIndex());

        mock.perform(get("/actual_registration/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(actualRegistrationId)),
                        jsonPath("$.index", is(actualRegistrationIndex)),
                        jsonPath("$.city", is(actualRegistration.getCity())),
                        jsonPath("$.region", is(actualRegistration.getRegion())),
                        jsonPath("$.street", is(actualRegistration.getStreet())),
                        jsonPath("$.country", is(actualRegistration.getCountry())),
                        jsonPath("$.district", is(actualRegistration.getDistrict())),
                        jsonPath("$.locality", is(actualRegistration.getLocality())),
                        jsonPath("$.houseBlock", is(actualRegistration.getHouseBlock())),
                        jsonPath("$.flatNumber", is(actualRegistration.getFlatNumber())),
                        jsonPath("$.houseNumber", is(actualRegistration.getHouseNumber()))
                );
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Пользователя нет")).when(service).read(any());

        mock.perform(get("/actual_registration/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Пользователя нет")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id позитивный сценарий")
    void readAllTest() throws Exception {
        final List<ActualRegistrationDto> actualRegistrations = returnActualRegistrations();

        final var zeroActualRegistration = actualRegistrations.get(0);
        final var oneActualRegistration = actualRegistrations.get(1);

        final int zeroActualRegistrationId = getIntFromLong(zeroActualRegistration.getId());
        final int zeroActualRegistrationIndex = getIntFromLong(zeroActualRegistration.getIndex());

        final int oneActualRegistrationId = getIntFromLong(oneActualRegistration.getId());
        final int oneActualRegistrationIndex = getIntFromLong(oneActualRegistration.getIndex());

        doReturn(actualRegistrations).when(service).readAll(any());

        mock.perform(get("/actual_registration?id=1&id=2")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(actualRegistrations.size())),
                jsonPath("$.[0].id", is(zeroActualRegistrationId)),
                jsonPath("$.[0].index", is(zeroActualRegistrationIndex)),
                jsonPath("$.[0].city", is(zeroActualRegistration.getCity())),
                jsonPath("$.[0].region", is(zeroActualRegistration.getRegion())),
                jsonPath("$.[0].street", is(zeroActualRegistration.getStreet())),
                jsonPath("$.[0].country", is(zeroActualRegistration.getCountry())),
                jsonPath("$.[0].district", is(zeroActualRegistration.getDistrict())),
                jsonPath("$.[0].locality", is(zeroActualRegistration.getLocality())),
                jsonPath("$.[0].houseBlock", is(zeroActualRegistration.getHouseBlock())),
                jsonPath("$.[0].flatNumber", is(zeroActualRegistration.getFlatNumber())),
                jsonPath("$.[0].houseNumber", is(zeroActualRegistration.getHouseNumber())),
                jsonPath("$.[1].id", is(oneActualRegistrationId)),
                jsonPath("$.[1].index", is(oneActualRegistrationIndex)),
                jsonPath("$.[1].city", is(oneActualRegistration.getCity())),
                jsonPath("$.[1].region", is(oneActualRegistration.getRegion())),
                jsonPath("$.[1].street", is(oneActualRegistration.getStreet())),
                jsonPath("$.[1].country", is(oneActualRegistration.getCountry())),
                jsonPath("$.[1].district", is(oneActualRegistration.getDistrict())),
                jsonPath("$.[1].locality", is(oneActualRegistration.getLocality())),
                jsonPath("$.[1].houseBlock", is(oneActualRegistration.getHouseBlock())),
                jsonPath("$.[1].flatNumber", is(oneActualRegistration.getFlatNumber())),
                jsonPath("$.[1].houseNumber", is(oneActualRegistration.getHouseNumber()))
        );
    }

    private List<ActualRegistrationDto> returnActualRegistrations() {
        return List.of(
                actualRegistrationSupplier.getDto(ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                        WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO
                ),
                actualRegistrationSupplier.getDto(TWO, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                        WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO
                )
        );
    }

    @Test
    @DisplayName("чтение по нескольким id негативный сценарий")
    void readAllNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Ошибка в параметрах")).when(service).readAll(any());

        mock.perform(get("/actual_registration?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string("Ошибка в параметрах")
                );
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(actualRegistration).when(service).update(anyLong(), any());

        response = mock.perform(put("/actual_registration/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(actualRegistration))
        );

        final int actualRegistrationId = getIntFromLong(actualRegistration.getId());
        final int actualRegistrationIndex = getIntFromLong(actualRegistration.getIndex());

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(actualRegistrationId)),
                jsonPath("$.index", is(actualRegistrationIndex)),
                jsonPath("$.city", is(actualRegistration.getCity())),
                jsonPath("$.region", is(actualRegistration.getRegion())),
                jsonPath("$.street", is(actualRegistration.getStreet())),
                jsonPath("$.country", is(actualRegistration.getCountry())),
                jsonPath("$.district", is(actualRegistration.getDistrict())),
                jsonPath("$.locality", is(actualRegistration.getLocality())),
                jsonPath("$.houseBlock", is(actualRegistration.getHouseBlock())),
                jsonPath("$.flatNumber", is(actualRegistration.getFlatNumber())),
                jsonPath("$.houseNumber", is(actualRegistration.getHouseNumber()))
        );
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Обновление невозможно")).when(service).update(anyLong(), any());

        response = mock.perform(put("/actual_registration/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(actualRegistration))
        );

        response.andExpectAll(status().isNotFound(),
                content().string("Обновление невозможно")
        );
    }
}
