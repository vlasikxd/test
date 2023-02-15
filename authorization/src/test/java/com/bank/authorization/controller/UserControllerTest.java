package com.bank.authorization.controller;

import com.bank.authorization.ParentTest;
import com.bank.authorization.dto.UserDto;
import com.bank.authorization.service.UserService;
import com.bank.common.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

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

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserControllerTest extends ParentTest {

    private static UserDto user;
    private final ObjectMapper mapper;
    private final MockMvc mock;

    private ResultActions response;

    @MockBean
    private UserService service;

    @BeforeAll
    static void init() {
        user = getUserDto(ONE, ROLE_USER, PASSWORD, ONE);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() throws Exception {
        doReturn(user).when(service).save(any());

        response = mock.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)));

        response.andExpectAll(status().isOk(),
                jsonPath("$.role", is(user.getRole())),
                jsonPath("$.password", is(user.getPassword())),
                jsonPath("$.profileId", is(user.getProfileId().intValue()))
        );
    }

    @Test
    @DisplayName("сохранение негативный сценарий")
    void saveNegativeTest() throws Exception {
        doThrow(new ValidationException("Неверные данные")).when(service).save(any());

        response = mock.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)));

        response.andExpectAll(status().isUnprocessableEntity(),
                content().string("Неверные данные")
        );
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() throws Exception {
        doReturn(user).when(service).read(any());

        mock.perform(get("/users/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(user.getId().intValue())),
                        jsonPath("$.role", is(user.getRole())),
                        jsonPath("$.password", is(user.getPassword())),
                        jsonPath("$.profileId", is(user.getProfileId().intValue()))
                );
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Пользователя нет")).when(service).read(any());

        mock.perform(get("/users/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string("Пользователя нет")
                );
    }

    @Test
    @DisplayName("чтение всех позитивный сценарий")
    void readAllTest() throws Exception {
        doReturn(Collections.singletonList(user)).when(service).readAll(any());

        mock.perform(get("/users?id=1")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(1)),
                jsonPath("$.[0].id", is(user.getId().intValue())),
                jsonPath("$.[0].role", is(user.getRole())),
                jsonPath("$.[0].password", is(user.getPassword())),
                jsonPath("$.[0].profileId", is(user.getProfileId().intValue()))
        );
    }

    @Test
    @DisplayName("чтение всех негативный сценарий")
    void readAllNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Ошибка в параметрах")).when(service).readAll(any());

        mock.perform(get("/users?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string("Ошибка в параметрах")
                );
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() throws Exception {
        doReturn(user).when(service).update(anyLong(), any());

        response = mock.perform(put("/users/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)));

        response.andExpectAll(status().isOk(),
                jsonPath("$.id", is(user.getId().intValue())),
                jsonPath("$.role", is(user.getRole())),
                jsonPath("$.password", is(user.getPassword())),
                jsonPath("$.profileId", is(user.getProfileId().intValue()))
        );
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException("Обновление невозможно")).when(service).update(anyLong(), any());

        response = mock.perform(put("/users/{id}", ONE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)));

        response.andExpectAll(status().isNotFound(),
                content().string("Обновление невозможно")
        );
    }
}
