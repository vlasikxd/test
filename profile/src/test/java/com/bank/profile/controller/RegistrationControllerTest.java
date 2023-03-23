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

@WebMvcTest(RegistrationController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RegistrationControllerTest extends ParentTest {

    private static RegistrationDto registration;
    private static RegistrationSupplier registrationSupplier;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private RegistrationService service;

    @BeforeAll
    static void init() {
        registrationSupplier = new RegistrationSupplier();

        registration = registrationSupplier.getDto(ONE, WHITESPACE, TWO);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(registration).when(service).save(any());

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.index", is(getIntFromLong(registration.getIndex()))),
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
    @DisplayName("сохранение неверных данных, негативный сценарий")
    void createIncorrectDataNegativeTest() throws Exception {
        final String exceptionMessage = "Неверные данные";

        doThrow(new ValidationException(exceptionMessage)).when(service).save(any());

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpectAll(status().isUnprocessableEntity(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("сохранение некорректного json, негативный сценарий")
    void createIncorrectJsonNegativeTest() throws Exception {
        final String exceptionMessage = "Некорректный json";

        doThrow(new HttpMessageNotReadableException(exceptionMessage)).when(service).save(any());

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(registration).when(service).read(any());

        final int id = getIntFromLong(registration.getId());
        final int index = getIntFromLong(registration.getIndex());

        mockMvc.perform(get("/registration/{id}", ONE))
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
    @DisplayName("чтение несуществующего id, негативный сценарий")
    void readNotExistIdNegativeTest() throws Exception {
        final String exceptionMessage = "accountDetailsId с данным идентификатором не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage)).when(service).read(any());

        mockMvc.perform(get("/registration/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по некорректному id, негативный сценарий")
    void readIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).read(any());

        mockMvc.perform(get("/registration/{id}", "id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {

        final List<RegistrationDto> registrations = returnRegistrations();

        final var zeroRegistration = registrations.get(0);
        final var oneRegistration = registrations.get(1);

        final int zeroId = getIntFromLong(zeroRegistration.getId());
        final int zeroIndex = getIntFromLong(zeroRegistration.getIndex());

        final int oneId = getIntFromLong(oneRegistration.getId());
        final int oneIndex = getIntFromLong(oneRegistration.getIndex());

        doReturn(registrations).when(service).readAll(any());

        mockMvc.perform(get("/registration?id=1")).andExpectAll(status().isOk(),
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
                registrationSupplier.getDto(ONE, WHITESPACE, TWO),
                registrationSupplier.getDto(TWO, WHITESPACE, TWO)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id, негативный сценарий")
    void readAllNoUserNegativeTest() throws Exception {
        final String exceptionMessage = "Ошибка в переданных параметрах, пользователи(ь) не найден(ы)";

        doThrow(new EntityNotFoundException(exceptionMessage))
                .when(service).readAll(any());

        mockMvc.perform(get("/registration?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по нескольким некорректным id, негативный сценарий")
    void readAllIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).readAll(any());

        mockMvc.perform(get("/registration?id=1&id=id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(registration).when(service).update(anyLong(), any());

        final int id = getIntFromLong(registration.getId());
        final int index = getIntFromLong(registration.getIndex());

        mockMvc.perform(put("/registration/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
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
    @DisplayName("обновление несуществующего registration, негативный сценарий")
    void updateNoRegistrationIdNegativeTest() throws Exception {
        final String exceptionMessage = "Обновление невозможно, пользователь не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage))
                .when(service).update(anyLong(), any());

        mockMvc.perform(put("/registration/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
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

        mockMvc.perform(put("/registration/{id}", TWO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }
}
