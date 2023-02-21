package com.bank.profile.controller;

import com.bank.common.exception.ValidationException;
import com.bank.profile.ParentTest;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
import com.bank.profile.supplier.RegistrationSupplier;
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

@WebMvcTest(RegistrationController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RegistrationControllerTest extends ParentTest {

    private static RegistrationDto registration;
    private static RegistrationSupplier registrationSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mock;

    private ResultActions response;

    @MockBean
    private RegistrationService service;

    @BeforeAll
    static void init() {
        registrationSupplier = new RegistrationSupplier();

        registration = registrationSupplier.getDto(ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(registration).when(service).save(any());

        response = mock.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registration))
        );

        final int index = getIntFromLong(registration.getIndex());

        response.andExpectAll(
                status().isOk(),
                jsonPath("$.index", is(index)),
                jsonPath("$.city", is(registration.getCity())),
                jsonPath("$.region", is(registration.getRegion())),
                jsonPath("$.street", is(registration.getStreet())),
                jsonPath("$.country", is(registration.getCountry())),
                jsonPath("$.district", is(registration.getDistrict())),
                jsonPath("$.locality", is(registration.getLocality())),
                jsonPath("$.houseBlock", is(registration.getHouseBlock())),
                jsonPath("$.flatNumber", is(registration.getFlatNumber())),
                jsonPath("$.houseNumber", is(registration.getHouseNumber()))
        );
    }

    @Test
    @DisplayName("сохранение негативный сценарий")
    void saveNegativeTest() throws Exception {
        doThrow(new ValidationException("Неверные данные")).when(service).save(any());

        response = mock.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registration))
        );

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string("Неверные данные")
        );
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() throws Exception {
        doReturn(registration).when(service).read(any());

        final int id = getIntFromLong(registration.getId());
        final int index = getIntFromLong(registration.getIndex());

        mock.perform(get("/registration/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.index", is(index)),
                        jsonPath("$.city", is(registration.getCity())),
                        jsonPath("$.region", is(registration.getRegion())),
                        jsonPath("$.street", is(registration.getStreet())),
                        jsonPath("$.country", is(registration.getCountry())),
                        jsonPath("$.district", is(registration.getDistrict())),
                        jsonPath("$.locality", is(registration.getLocality())),
                        jsonPath("$.houseBlock", is(registration.getHouseBlock())),
                        jsonPath("$.flatNumber", is(registration.getFlatNumber())),
                        jsonPath("$.houseNumber", is(registration.getHouseNumber()))
                );
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Пользователя нет")).when(service).read(any());

        mock.perform(get("/registration/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Пользователя нет")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id позитивный сценарий")
    void readAllTest() throws Exception {

        final List<RegistrationDto> registrations = returnRegistrations();

        final var zeroRegistration = registrations.get(0);
        final var oneRegistration = registrations.get(1);

        final int zeroId = getIntFromLong(zeroRegistration.getId());
        final int zeroIndex = getIntFromLong(zeroRegistration.getIndex());

        final int oneId = getIntFromLong(oneRegistration.getId());
        final int oneIndex = getIntFromLong(oneRegistration.getIndex());

        doReturn(registrations).when(service).readAll(any());

        mock.perform(get("/registration?id=1")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(registrations.size())),
                jsonPath("$.[0].id", is(zeroId)),
                jsonPath("$.[0].index", is(zeroIndex)),
                jsonPath("$.[0].city", is(zeroRegistration.getCity())),
                jsonPath("$.[0].region", is(zeroRegistration.getRegion())),
                jsonPath("$.[0].street", is(zeroRegistration.getStreet())),
                jsonPath("$.[0].country", is(zeroRegistration.getCountry())),
                jsonPath("$.[0].district", is(zeroRegistration.getDistrict())),
                jsonPath("$.[0].locality", is(zeroRegistration.getLocality())),
                jsonPath("$.[0].houseBlock", is(zeroRegistration.getHouseBlock())),
                jsonPath("$.[0].flatNumber", is(zeroRegistration.getFlatNumber())),
                jsonPath("$.[0].houseNumber", is(zeroRegistration.getHouseNumber())),
                jsonPath("$.[1].id", is(oneId)),
                jsonPath("$.[1].index", is(oneIndex)),
                jsonPath("$.[1].city", is(oneRegistration.getCity())),
                jsonPath("$.[1].region", is(oneRegistration.getRegion())),
                jsonPath("$.[1].street", is(oneRegistration.getStreet())),
                jsonPath("$.[1].country", is(oneRegistration.getCountry())),
                jsonPath("$.[1].district", is(oneRegistration.getDistrict())),
                jsonPath("$.[1].locality", is(oneRegistration.getLocality())),
                jsonPath("$.[1].houseBlock", is(oneRegistration.getHouseBlock())),
                jsonPath("$.[1].flatNumber", is(oneRegistration.getFlatNumber())),
                jsonPath("$.[1].houseNumber", is(oneRegistration.getHouseNumber()))
        );
    }

    private List<RegistrationDto> returnRegistrations() {
        return List.of(
                registrationSupplier.getDto(ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                        WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO),
                registrationSupplier.getDto(TWO, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                        WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, TWO)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id негативный сценарий")
    void readAllNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Ошибка в параметрах")).when(service).readAll(any());

        mock.perform(get("/registration?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string("Ошибка в параметрах")
                );
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(registration).when(service).update(anyLong(), any());

        response = mock.perform(put("/registration/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registration))
        );

        final int id = getIntFromLong(registration.getId());
        final int index = getIntFromLong(registration.getIndex());

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(id)),
                jsonPath("$.index", is(index)),
                jsonPath("$.city", is(registration.getCity())),
                jsonPath("$.region", is(registration.getRegion())),
                jsonPath("$.street", is(registration.getStreet())),
                jsonPath("$.country", is(registration.getCountry())),
                jsonPath("$.district", is(registration.getDistrict())),
                jsonPath("$.locality", is(registration.getLocality())),
                jsonPath("$.houseBlock", is(registration.getHouseBlock())),
                jsonPath("$.flatNumber", is(registration.getFlatNumber())),
                jsonPath("$.houseNumber", is(registration.getHouseNumber()))
        );
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Обновление невозможно")).when(service).update(anyLong(), any());

        response = mock.perform(put("/registration/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registration))
        );

        response.andExpectAll(status().isNotFound(),
                content().string("Обновление невозможно")
        );
    }
}
