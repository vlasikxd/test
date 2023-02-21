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

@WebMvcTest(PassportController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PassportControllerTest extends ParentTest {

    private static PassportDto passport;
    private static PassportSupplier passportSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mock;

    private ResultActions response;

    @MockBean
    private PassportService service;

    @BeforeAll
    static void init() {
        passportSupplier = new PassportSupplier();

        passport = passportSupplier.getDto(ONE, INT_ONE, ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, LOCAL_DATE,
                WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(passport).when(service).save(any());

        response = mock.perform(post("/passport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(passport))
        );

        final int number = getIntFromLong(passport.getNumber());
        final String birthDate = LocalDateToString(passport.getBirthDate());
        final String dateOfIssue = LocalDateToString(passport.getBirthDate());
        final String expirationDate = LocalDateToString(passport.getBirthDate());

        response.andExpectAll(
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
    @DisplayName("сохранение негативный сценарий")
    void saveNegativeTest() throws Exception {
        doThrow(new ValidationException("Неверные данные")).when(service).save(any());

        response = mock.perform(post("/passport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(passport))
        );

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string("Неверные данные")
        );
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() throws Exception {
        doReturn(passport).when(service).read(any());

        final int id = getIntFromLong(passport.getId());
        final int number = getIntFromLong(passport.getNumber());
        final String birthDate = LocalDateToString(passport.getBirthDate());
        final String dateOfIssue = LocalDateToString(passport.getBirthDate());
        final String expirationDate = LocalDateToString(passport.getBirthDate());

        mock.perform(get("/passport/{id}", ONE))
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
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Пользователя нет")).when(service).read(any());

        mock.perform(get("/passport/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Пользователя нет")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id позитивный сценарий")
    void readAllTest() throws Exception {
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

        mock.perform(get("/passport?id=1&id=2")).andExpectAll(status().isOk(),
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
                passportSupplier.getDto(ONE, INT_ONE, ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, LOCAL_DATE,
                        WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null),
                passportSupplier.getDto(TWO, INT_ONE, ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, LOCAL_DATE,
                        WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id негативный сценарий")
    void readAllNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Ошибка в параметрах")).when(service).readAll(any());

        mock.perform(get("/passport?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string("Ошибка в параметрах")
                );
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(passport).when(service).update(anyLong(), any());

        response = mock.perform(put("/passport/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(passport))
        );

        final int id = getIntFromLong(passport.getId());
        final int number = getIntFromLong(passport.getNumber());
        final String birthDate = LocalDateToString(passport.getBirthDate());
        final String dateOfIssue = LocalDateToString(passport.getBirthDate());
        final String expirationDate = LocalDateToString(passport.getBirthDate());

        response.andExpectAll(status().isOk(),
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
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Обновление невозможно")).when(service).update(anyLong(), any());

        response = mock.perform(put("/passport/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(passport)));

        response.andExpectAll(status().isNotFound(),
                content().string("Обновление невозможно")
        );
    }
}
