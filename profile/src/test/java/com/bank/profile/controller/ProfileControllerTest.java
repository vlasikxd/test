package com.bank.profile.controller;

import com.bank.common.exception.ValidationException;
import com.bank.profile.ParentTest;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.service.ProfileService;
import com.bank.profile.supplier.ProfileSupplier;
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

@WebMvcTest(ProfileController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProfileControllerTest extends ParentTest {

    private static ProfileDto profile;
    private static ProfileSupplier profileSupplier;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private ProfileService service;

    @BeforeAll
    static void init() {
        profileSupplier = new ProfileSupplier();

        profile = profileSupplier.getDto(ONE, ONE, WHITESPACE, WHITESPACE);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(profile).when(service).save(any());

        mockMvc.perform(post("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(profile)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.inn", is(getIntFromLong(profile.getInn()))),
                        jsonPath("$.snils", is(getIntFromLong(profile.getSnils()))),
                        jsonPath("$.phoneNumber", is(getIntFromLong(profile.getPhoneNumber()))),
                        jsonPath("$.email", is(profile.getEmail())),
                        jsonPath("$.passport", is(profile.getPassport())),
                        jsonPath("$.nameOnCard", is(profile.getNameOnCard())),
                        jsonPath("$.actualRegistration", is(profile.getActualRegistration()))
                );
    }

    @Test
    @DisplayName("сохранение неверных данных, негативный сценарий")
    void createIncorrectDataNegativeTest() throws Exception {
        final String exceptionMessage = "Неверные данные";

        doThrow(new ValidationException(exceptionMessage)).when(service).save(any());

        mockMvc.perform(post("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(profile)))
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

        mockMvc.perform(post("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(profile).when(service).read(any());

        final int id = getIntFromLong(profile.getId());
        final int inn = getIntFromLong(profile.getInn());
        final int snils = getIntFromLong(profile.getSnils());
        final int phoneNumber = getIntFromLong(profile.getPhoneNumber());

        mockMvc.perform(get("/profile/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.inn", is(inn)),
                        jsonPath("$.snils", is(snils)),
                        jsonPath("$.phoneNumber", is(phoneNumber)),
                        jsonPath("$.email", is(profile.getEmail())),
                        jsonPath("$.passport", is(profile.getPassport())),
                        jsonPath("$.nameOnCard", is(profile.getNameOnCard())),
                        jsonPath("$.actualRegistration", is(profile.getActualRegistration()))
                );
    }

    @Test
    @DisplayName("чтение несуществующего id, негативный сценарий")
    void readNotExistIdNegativeTest() throws Exception {
        final String exceptionMessage = "accountDetailsId с данным идентификатором не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage)).when(service).read(any());

        mockMvc.perform(get("/profile/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по некорректному id, негативный сценарий")
    void readIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).read(any());

        mockMvc.perform(get("/profile/{id}", "id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }


    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        final List<ProfileDto> profiles = returnProfiles();

        final var zeroProfile = profiles.get(0);
        final var oneProfile = profiles.get(1);

        final int zeroId = getIntFromLong(zeroProfile.getId());
        final int zeroInn = getIntFromLong(zeroProfile.getInn());
        final int zeroSnils = getIntFromLong(zeroProfile.getSnils());
        final int zeroPhoneNumber = getIntFromLong(zeroProfile.getPhoneNumber());

        final int oneId = getIntFromLong(oneProfile.getId());
        final int oneInn = getIntFromLong(oneProfile.getInn());
        final int oneSnils = getIntFromLong(oneProfile.getSnils());
        final int onePhoneNumber = getIntFromLong(oneProfile.getPhoneNumber());

        doReturn(profiles).when(service).readAll(any());

        mockMvc.perform(get("/profile?id=1&id=2")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(profiles.size())),
                jsonPath("$.[0].id", is(zeroId)),
                jsonPath("$.[0].inn", is(zeroInn)),
                jsonPath("$.[0].snils", is(zeroSnils)),
                jsonPath("$.[0].phoneNumber", is(zeroPhoneNumber)),
                jsonPath("$.[0].email", is(zeroProfile.getEmail())),
                jsonPath("$.[0].passport", is(zeroProfile.getPassport())),
                jsonPath("$.[0].nameOnCard", is(zeroProfile.getNameOnCard())),
                jsonPath("$.[0].actualRegistration", is(zeroProfile.getActualRegistration())),
                jsonPath("$.[1].id", is(oneId)),
                jsonPath("$.[1].inn", is(oneInn)),
                jsonPath("$.[1].snils", is(oneSnils)),
                jsonPath("$.[1].phoneNumber", is(onePhoneNumber)),
                jsonPath("$.[1].email", is(oneProfile.getEmail())),
                jsonPath("$.[1].passport", is(oneProfile.getPassport())),
                jsonPath("$.[1].nameOnCard", is(oneProfile.getNameOnCard())),
                jsonPath("$.[1].actualRegistration", is(oneProfile.getActualRegistration()))
        );
    }

    private List<ProfileDto> returnProfiles() {
        return List.of(
                profileSupplier.getDto(ONE, ONE, WHITESPACE, WHITESPACE),
                profileSupplier.getDto(TWO, TWO, WHITESPACE, WHITESPACE)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id, негативный сценарий")
    void readAllNoUserNegativeTest() throws Exception {
        final String exceptionMessage = "Ошибка в переданных параметрах, пользователи(ь) не найден(ы)";

        doThrow(new EntityNotFoundException(exceptionMessage))
                .when(service).readAll(any());

        mockMvc.perform(get("/profile?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string(exceptionMessage)
                );
    }

    @Test
    @DisplayName("чтение по нескольким некорректным id, негативный сценарий")
    void readAllIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).readAll(any());

        mockMvc.perform(get("/profile?id=1&id=id"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(profile).when(service).update(anyLong(), any());

        final int id = getIntFromLong(profile.getId());
        final int inn = getIntFromLong(profile.getInn());
        final int snils = getIntFromLong(profile.getSnils());
        final int phoneNumber = getIntFromLong(profile.getPhoneNumber());

        mockMvc.perform(put("/profile/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(profile)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(id)),
                        jsonPath("$.inn", is(inn)),
                        jsonPath("$.snils", is(snils)),
                        jsonPath("$.phoneNumber", is(phoneNumber)),
                        jsonPath("$.email", is(profile.getEmail())),
                        jsonPath("$.passport", is(profile.getPassport())),
                        jsonPath("$.nameOnCard", is(profile.getNameOnCard())),
                        jsonPath("$.actualRegistration", is(profile.getActualRegistration()))
                );
    }

    @Test
    @DisplayName("обновление несуществующего profile, негативный сценарий")
    void updateNoProfileNegativeTest() throws Exception {
        final String exceptionMessage = "Обновление невозможно, пользователь не найден!";

        doThrow(new EntityNotFoundException(exceptionMessage))
                .when(service).update(anyLong(), any());

        mockMvc.perform(put("/profile/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(profile)))
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

        mockMvc.perform(put("/profile/{id}", TWO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(exceptionMessage)
                );
    }
}
