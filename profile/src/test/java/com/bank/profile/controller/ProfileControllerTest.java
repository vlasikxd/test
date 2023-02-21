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

@WebMvcTest(ProfileController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProfileControllerTest extends ParentTest {

    private static ProfileDto profile;
    private static ProfileSupplier profileSupplier;

    private final ObjectMapper mapper;
    private final MockMvc mock;

    private ResultActions response;

    @MockBean
    private ProfileService service;

    @BeforeAll
    static void init() {
        profileSupplier = new ProfileSupplier();

        profile = profileSupplier.getDto(ONE, ONE, WHITESPACE, WHITESPACE, ONE, ONE, null, null);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(profile).when(service).save(any());

        response = mock.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(profile))
        );

        final int inn = getIntFromLong(profile.getInn());
        final int snils = getIntFromLong(profile.getSnils());
        final int phoneNumber = getIntFromLong(profile.getPhoneNumber());

        response.andExpectAll(
                status().isOk(),
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
    @DisplayName("сохранение негативный сценарий")
    void saveNegativeTest() throws Exception {
        doThrow(new ValidationException("Неверные данные")).when(service).save(any());

        response = mock.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(profile))
        );

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string("Неверные данные")
        );
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() throws Exception {
        doReturn(profile).when(service).read(any());

        final int id = getIntFromLong(profile.getId());
        final int inn = getIntFromLong(profile.getInn());
        final int snils = getIntFromLong(profile.getSnils());
        final int phoneNumber = getIntFromLong(profile.getPhoneNumber());

        mock.perform(get("/profile/{id}", ONE))
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
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Пользователя нет")).when(service).read(any());

        mock.perform(get("/profile/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Пользователя нет")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id позитивный сценарий")
    void readAllTest() throws Exception {
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

        mock.perform(get("/profile?id=1&id=2")).andExpectAll(status().isOk(),
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
                profileSupplier.getDto(ONE, ONE, WHITESPACE, WHITESPACE, ONE, ONE, null, null),
                profileSupplier.getDto(TWO, TWO, WHITESPACE, WHITESPACE, TWO, TWO, null, null)
        );
    }

    @Test
    @DisplayName("чтение по нескольким id негативный сценарий")
    void readAllNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Ошибка в параметрах")).when(service).readAll(any());

        mock.perform(get("/profile?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string("Ошибка в параметрах")
                );
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(profile).when(service).update(anyLong(), any());

        response = mock.perform(put("/profile/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(profile))
        );

        final int id = getIntFromLong(profile.getId());
        final int inn = getIntFromLong(profile.getInn());
        final int snils = getIntFromLong(profile.getSnils());
        final int phoneNumber = getIntFromLong(profile.getPhoneNumber());

        response.andExpectAll(status().isOk(),
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
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Обновление невозможно")).when(service).update(anyLong(), any());

        response = mock.perform(put("/profile/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(profile))
        );

        response.andExpectAll(status().isNotFound(),
                content().string("Обновление невозможно")
        );
    }
}
