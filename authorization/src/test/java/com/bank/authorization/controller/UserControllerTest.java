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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.bank.authorization.supplier.UserSupplier.getUserDto;
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
    private final MockMvc mockMvc;

    private ResultActions response;

    @MockBean
    private UserService service;

    @BeforeAll
    static void setUp() {
        user = getUserDto(ONE, USERNAME, ROLE_USER, PASSWORD, ONE);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
        doReturn(user).when(service).save(any());

        response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.username", is(user.getUsername())),
                        jsonPath("$.role", is(user.getRole().name())),
                        jsonPath("$.password", is(user.getPassword())),
                        jsonPath("$.profileId", is(user.getProfileId().intValue()))
                );
    }

    @Test
    @DisplayName("сохранение неверных данных, негативный сценарий")
    void createIncorrectDataNegativeTest() throws Exception {
        String message = "Неверные данные";
        doThrow(new ValidationException(message)).when(service).save(any());

        response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpectAll(status().isUnprocessableEntity(),
                        content().string(message)
                );
    }

    @Test
    @DisplayName("сохранение некорректного json, негативный сценарий")
    void createIncorrectJsonNegativeTest() throws Exception {
        String message = "Обновление невозможно, некорректные данные!";
        var httpMessageNotReadableException = new HttpMessageNotReadableException(
                message, new MockClientHttpResponse(new byte[]{}, HttpStatus.MULTI_STATUS)
        );
        doThrow(httpMessageNotReadableException)
                .when(service)
                .save(any());

        response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(message));
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() throws Exception {
        doReturn(user).when(service).read(any());

        mockMvc.perform(get("/users/{id}", ONE))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(user.getId().intValue())),
                        jsonPath("$.username", is(user.getUsername())),
                        jsonPath("$.role", is(user.getRole().name())),
                        jsonPath("$.password", is(user.getPassword())),
                        jsonPath("$.profileId", is(user.getProfileId().intValue()))
                );
    }

    @Test
    @DisplayName("чтение несуществующего пользователя, негативный сценарий")
    void readNoUserNegativeTest() throws Exception {
        String message = "Пользователя нет";
        doThrow(new EntityNotFoundException(message)).when(service).read(any());

        mockMvc.perform(get("/users/{id}", ONE))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(message)
                );
    }

    @Test
    @DisplayName("чтение по некорректному id, негативный сценарий")
    void readIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).read(any());

        mockMvc.perform(get("/users/{id}", "NotANumber"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("Некорректно указан id")
                );
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        doReturn(List.of(user)).when(service).readAll(any());

        mockMvc.perform(get("/users?id=1")).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(1)),
                jsonPath("$.[0].role", is(user.getRole().name())),
                jsonPath("$.[0].username", is(user.getUsername())),
                jsonPath("$.[0].id", is(user.getId().intValue())),
                jsonPath("$.[0].password", is(user.getPassword())),
                jsonPath("$.[0].profileId", is(user.getProfileId().intValue()))
        );
    }

    @Test
    @DisplayName("чтение по нескольким id, негативный сценарий")
    void readAllNoUserNegativeTest() throws Exception {
        String message = "Ошибка в переданных параметрах, пользователь(и) не найден(ы)";

        doThrow(new EntityNotFoundException(message))
                .when(service)
                .readAll(any()
                );

        mockMvc.perform(get("/users?id=1"))
                .andExpectAll(status().isNotFound(),
                        content().string(message)
                );
    }

    @Test
    @DisplayName("чтение по нескольким некорректным id, негативный сценарий")
    void readAllIncorrectIdNegativeTest() throws Exception {
        doThrow(MethodArgumentTypeMismatchException.class).when(service).readAll(any());

        mockMvc.perform(get("/users?id=NotANumber"))
                .andExpectAll(status().isBadRequest(),
                        content().string("Некорректно указан id"));
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        doReturn(user).when(service).update(anyLong(), any());

        response = mockMvc.perform(put("/users/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id", is(user.getId().intValue())),
                        jsonPath("$.username", is(user.getUsername())),
                        jsonPath("$.role", is(user.getRole().name())),
                        jsonPath("$.password", is(user.getPassword())),
                        jsonPath("$.profileId", is(user.getProfileId().intValue()))
                );
    }

    @Test
    @DisplayName("обновление, негативный сценарий")
    void updateNoUserNegativeTest() throws Exception {
        String message = "Обновление невозможно, пользователь не найден!";

        doThrow(new EntityNotFoundException(message))
                .when(service).update(anyLong(), any()
                );

        response = mockMvc.perform(put("/users/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpectAll(status().isNotFound(),
                        content().string(message)
                );
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("обновление, некорректный json, негативный сценарий")
    void updateIncorrectJsonNegativeTest() throws Exception {
        String message = "Обновление невозможно, некорректные данные!";
        doThrow(new HttpMessageNotReadableException(message))
                .when(service)
                .update(anyLong(), any());

        response = mockMvc.perform(put("/users/{id}", ONE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpectAll(status().isBadRequest(),
                        content().string(message)
                );
    }
}
