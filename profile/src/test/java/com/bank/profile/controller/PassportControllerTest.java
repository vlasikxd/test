package com.bank.profile.controller;

import com.bank.common.exception.ValidationException;
import com.bank.profile.ParentTest;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.service.PassportService;
import com.bank.profile.supplier.PassportSupplier;
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

@WebMvcTest(PassportController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PassportControllerTest extends ParentTest {

    private static PassportDto passport;
    private static PassportSupplier passportSupplier;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private PassportService service;

    @BeforeAll
    static void init() {
        passportSupplier = new PassportSupplier();

        passport = passportSupplier.getDto(ONE, WHITESPACE, WHITESPACE, LOCAL_DATE, null);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(passport).when(service).save(any());

        final int number = getIntFromLong(passport.getNumber());
        final String birthDate = LocalDateToString(passport.getBirthDate());
        final String dateOfIssue = LocalDateToString(passport.getBirthDate());
        final String expirationDate = LocalDateToString(passport.getBirthDate());

        mockMvc.perform(post("/passport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passport)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.number", is(number)),
                        jsonPath("$.birthDate", is(birthDate)),
                        jsonPath("$.dateOfIssue", is(dateOfIssue)),
                        jsonPath("$.series", is(passport.getSeries())),
                        jsonPath("$.gender", is(passport.getGender())),
                        jsonPath("$.expirationDate", is(expirationDate)),
                        jsonPath("$.issuedBy", is(passport.getIssuedBy())),
                        jsonPath("$.lastName", is(passport.getLastName())),
                        jsonPath("$.firstName", is(passport.getFirstName())),
                        jsonPath("$.middleName", is(passport.getMiddleName())),
                        jsonPath("$.birthPlace", is(passport.getBirthPlace())),
                        jsonPath("$.divisionCode", is(passport.getDivisionCode())),
                        jsonPath("$.registration", is(passport.getRegistration()))
                );
    }

    @Test
    @DisplayName("сохранение неверных данных, негативный сценарий")
    void createIncorrectDataNegativeTest() throws Exception {
        final String exceptionMessage = "Неверные данные";

        doThrow(new ValidationException(exceptionMessage)).when(service).save(any());

        mockMvc.perform(post("/passport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passport)))
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

        mockMvc.perform(post("/passport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readTest() throws Exception {
        doReturn(passport).when(service).read(any());

        final int id = getIntFromLong(passport.getId());
        final int number = getIntFromLong(passport.getNumber());
        final String birthDate = LocalDateToString(passport.getBirthDate());
        final String dateOfIssue = LocalDateToString(passport.getBirthDate());
        final String expirationDate = LocalDateToString(passport.getBirthDate());

        mockMvc.perform(get("/passport/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.number", is(number)),
                        jsonPath("$.birthDate", is(birthDate)),
                        jsonPath("$.dateOfIssue", is(dateOfIssue)),
                        jsonPath("$.series", is(passport.getSeries())),
                        jsonPath("$.gender", is(passport.getGender())),
                        jsonPath("$.expirationDate", is(expirationDate)),
                        jsonPath("$.issuedBy", is(passport.getIssuedBy())),
                        jsonPath("$.lastName", is(passport.getLastName())),
                        jsonPath("$.firstName", is(passport.getFirstName())),
                        jsonPath("$.middleName", is(passport.getMiddleName())),
                        jsonPath("$.birthPlace", is(passport.getBirthPlace())),
                        jsonPath("$.divisionCode", is(passport.getDivisionCode())),
                        jsonPath("$.registration", is(passport.getRegistration()))
                );
    }

    @Test
    @DisplayName("чтение несуществующего id, негативный сценарий")
    void readNonExistIdNegativeTest() throws Exception {
        final String exceptionMessage = "accountDetailsId с данным идентификатором не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage)).when(service).read(any());

        mockMvc.perform(get("/passport/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по некорректному id, негативный сценарий")
    void readIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).read(any());

        mockMvc.perform(get("/passport/{id}", "id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<PassportDto> passports = returnPassports();

        final var zeroPassport = passports.get(0);
        final var onePassport = passports.get(1);

        final int zeroId = getIntFromLong(zeroPassport.getId());
        final int zeroNumber = getIntFromLong(zeroPassport.getNumber());
        final String zeroBirthDate = LocalDateToString(zeroPassport.getBirthDate());
        final String zeroDateOfIssue = LocalDateToString(zeroPassport.getBirthDate());
        final String zeroExpirationDate = LocalDateToString(zeroPassport.getBirthDate());

        final int oneId = getIntFromLong(onePassport.getId());
        final int oneNumber = getIntFromLong(onePassport.getNumber());
        final String oneBirthDate = LocalDateToString(onePassport.getBirthDate());
        final String oneDateOfIssue = LocalDateToString(onePassport.getBirthDate());
        final String oneExpirationDate = LocalDateToString(onePassport.getBirthDate());

        doReturn(passports).when(service).readAll(any());

        mockMvc.perform(get("/passport?id=1&id=2")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(passports.size())),
                jsonPath("$.[0].id", is(zeroId)),
                jsonPath("$.[0].number", is(zeroNumber)),
                jsonPath("$.[0].birthDate", is(zeroBirthDate)),
                jsonPath("$.[0].dateOfIssue", is(zeroDateOfIssue)),
                jsonPath("$.[0].series", is(passport.getSeries())),
                jsonPath("$.[0].gender", is(passport.getGender())),
                jsonPath("$.[0].issuedBy", is(passport.getIssuedBy())),
                jsonPath("$.[0].lastName", is(passport.getLastName())),
                jsonPath("$.[0].expirationDate", is(zeroExpirationDate)),
                jsonPath("$.[0].firstName", is(passport.getFirstName())),
                jsonPath("$.[0].middleName", is(passport.getMiddleName())),
                jsonPath("$.[0].birthPlace", is(passport.getBirthPlace())),
                jsonPath("$.[0].divisionCode", is(passport.getDivisionCode())),
                jsonPath("$.[0].registration", is(passport.getRegistration())),
                jsonPath("$.[1].id", is(oneId)),
                jsonPath("$.[1].number", is(oneNumber)),
                jsonPath("$.[1].birthDate", is(oneBirthDate)),
                jsonPath("$.[1].dateOfIssue", is(oneDateOfIssue)),
                jsonPath("$.[1].series", is(passport.getSeries())),
                jsonPath("$.[1].gender", is(passport.getGender())),
                jsonPath("$.[1].issuedBy", is(passport.getIssuedBy())),
                jsonPath("$.[1].lastName", is(passport.getLastName())),
                jsonPath("$.[1].expirationDate", is(oneExpirationDate)),
                jsonPath("$.[1].firstName", is(passport.getFirstName())),
                jsonPath("$.[1].middleName", is(passport.getMiddleName())),
                jsonPath("$.[1].birthPlace", is(passport.getBirthPlace())),
                jsonPath("$.[1].divisionCode", is(passport.getDivisionCode())),
                jsonPath("$.[1].registration", is(passport.getRegistration()))
        );
    }

    private List<PassportDto> returnPassports() {
        return List.of(
                passportSupplier.getDto(ONE, WHITESPACE, WHITESPACE, LOCAL_DATE, null),
                passportSupplier.getDto(TWO, WHITESPACE, WHITESPACE, LOCAL_DATE, null)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id, негативный сценарий")
    void readAllNoUserNegativeTest() throws Exception {
        final String exceptionMessage = "Ошибка в переданных параметрах, пользователи(ь) не найден(ы)";

        doThrow(new EntityNotFoundException(exceptionMessage))
                .when(service).readAll(any());

        mockMvc.perform(get("/passport?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по нескольким некорректным id, негативный сценарий")
    void readAllIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).readAll(any());

        mockMvc.perform(get("/passport?id=1&id=id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(passport).when(service).update(anyLong(), any());

        final int id = getIntFromLong(passport.getId());
        final int number = getIntFromLong(passport.getNumber());
        final String birthDate = LocalDateToString(passport.getBirthDate());
        final String dateOfIssue = LocalDateToString(passport.getBirthDate());
        final String expirationDate = LocalDateToString(passport.getBirthDate());

        mockMvc.perform(put("/passport/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passport)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.number", is(number)),
                        jsonPath("$.birthDate", is(birthDate)),
                        jsonPath("$.dateOfIssue", is(dateOfIssue)),
                        jsonPath("$.series", is(passport.getSeries())),
                        jsonPath("$.gender", is(passport.getGender())),
                        jsonPath("$.expirationDate", is(expirationDate)),
                        jsonPath("$.issuedBy", is(passport.getIssuedBy())),
                        jsonPath("$.lastName", is(passport.getLastName())),
                        jsonPath("$.firstName", is(passport.getFirstName())),
                        jsonPath("$.middleName", is(passport.getMiddleName())),
                        jsonPath("$.birthPlace", is(passport.getBirthPlace())),
                        jsonPath("$.divisionCode", is(passport.getDivisionCode())),
                        jsonPath("$.registration", is(passport.getRegistration()))
                );
    }

    @Test
    @DisplayName("обновление несуществующего passport, негативный сценарий")
    void updateNoPassportNegativeTest() throws Exception {
        final String exceptionMessage = "Обновление невозможно, пользователь не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage))
                .when(service).update(anyLong(), any());

        mockMvc.perform(put("/passport/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passport)))
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

        mockMvc.perform(put("/passport/{id}", TWO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }
}
