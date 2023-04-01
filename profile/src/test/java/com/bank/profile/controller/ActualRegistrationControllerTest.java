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

@WebMvcTest(ActualRegistrationController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ActualRegistrationControllerTest extends ParentTest {

    private static ActualRegistrationDto actualRegistration;
    private static ActualRegistrationSupplier actualRegistrationSupplier;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private ActualRegistrationService service;

    @BeforeAll
    static void init() {
        actualRegistrationSupplier = new ActualRegistrationSupplier();

        actualRegistration = actualRegistrationSupplier.getDto(
                ONE, WHITESPACE, NUMBER
        );
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(actualRegistration).when(service).save(any());

        final int index = getIntFromLong(actualRegistration.getIndex());

        mockMvc.perform(post("/actual_registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(actualRegistration)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.index", is(index)),
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
    @DisplayName("сохранение неверных данных, негативный сценарий")
    void createIncorrectDataNegativeTest() throws Exception {
        final String exceptionMessage = "Неверные данные";

        doThrow(new ValidationException(exceptionMessage)).when(service).save(any());

        mockMvc.perform(post("/actual_registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(actualRegistration)))
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

        mockMvc.perform(post("/actual_registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(actualRegistration).when(service).read(any());

        final int actualRegistrationId = getIntFromLong(actualRegistration.getId());
        final int actualRegistrationIndex = getIntFromLong(actualRegistration.getIndex());

        mockMvc.perform(get("/actual_registration/{id}", ONE))
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
    @DisplayName("чтение несуществующего id, негативный сценарий")
    void readNotExistIdNegativeTest() throws Exception {
        final String exceptionMessage = "accountDetailsId с данным идентификатором не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage)).when(service).read(any());

        mockMvc.perform(get("/actual_registration/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по некорректному id, негативный сценарий")
    void readIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).read(any());

        mockMvc.perform(get("/actual_registration/{id}", "id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<ActualRegistrationDto> actualRegistrations = returnActualRegistrations();

        final var zeroActualRegistration = actualRegistrations.get(0);
        final var oneActualRegistration = actualRegistrations.get(1);

        final int zeroActualRegistrationId = getIntFromLong(zeroActualRegistration.getId());
        final int zeroActualRegistrationIndex = getIntFromLong(zeroActualRegistration.getIndex());

        final int oneActualRegistrationId = getIntFromLong(oneActualRegistration.getId());
        final int oneActualRegistrationIndex = getIntFromLong(oneActualRegistration.getIndex());

        doReturn(actualRegistrations).when(service).readAll(any());

        mockMvc.perform(get("/actual_registration?id=1&id=2")).andExpectAll(status().isOk(),
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
                actualRegistrationSupplier.getDto(ONE, WHITESPACE, TWO
                ),
                actualRegistrationSupplier.getDto(TWO, WHITESPACE, TWO
                )
        );
    }

    @Test
    @DisplayName("чтение по нескольким id, негативный сценарий")
    void readAllNoUserNegativeTest() throws Exception {
        final String exceptionMessage = "Ошибка в переданных параметрах, пользователи(ь) не найден(ы)";

        doThrow(new EntityNotFoundException(exceptionMessage))
                .when(service).readAll(any());

        mockMvc.perform(get("/actual_registration?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по нескольким некорректным id, негативный сценарий")
    void readAllIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).readAll(any());

        mockMvc.perform(get("/actual_registration?id=1&id=id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(actualRegistration).when(service).update(anyLong(), any());

        final int actualRegistrationId = getIntFromLong(actualRegistration.getId());
        final int actualRegistrationIndex = getIntFromLong(actualRegistration.getIndex());

        mockMvc.perform(put("/actual_registration/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(actualRegistration)))
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
    @DisplayName("обновление несуществующего accountDetailsId, негативный сценарий")
    void updateNoRegistrationIdNegativeTest() throws Exception {
        final String exceptionMessage = "Обновление невозможно, пользователь не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage)).when(service).update(anyLong(), any());

        mockMvc.perform(put("/actual_registration/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(actualRegistration)))
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

        mockMvc.perform(put("/actual_registration/{id}", TWO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }
}
