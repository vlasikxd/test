package com.bank.authorization.mapper;

import com.bank.authorization.ParentTest;
import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest extends ParentTest {

    private final UserMapper MAPPER = new UserMapperImpl();

    private UserEntity user;
    private UserDto userDto;
    private UserDto userUpdateDto;
    private List<UserEntity> users;

    @BeforeEach
    void init() {
        userDto = getUserDto(ONE, ROLE_USER, PASSWORD, ONE);

        userUpdateDto = getUserDto(null, ROLE_ADMIN, PASSWORD_ADMIN, TWO);

        user = getUser(ONE, ROLE_USER, PASSWORD, ONE);

        users = getUsers(user);
    }

    @Test
    @DisplayName("маппинг к entity позитивный сценарий")
    void toEntityTest() {
        final UserEntity result = MAPPER.toEntity(userDto);

        assertAll(() -> {
            assertNotEquals(userDto.getId(), result.getId());
            assertEquals(userDto.getRole(), result.getRole());
            assertEquals(userDto.getPassword(), result.getPassword());
            assertEquals(userDto.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("маппинг к entity с параметром null")
    void toEntityNullTest() {
        assertNull(MAPPER.toEntity(null));
    }

    @Test
    @DisplayName("маппинг к дто позитивный сценарий")
    void toDtoTest() {
        final UserDto result = MAPPER.toDto(user);

        assertAll(() -> {
            assertEquals(user.getId(), result.getId());
            assertEquals(user.getRole(), result.getRole());
            assertEquals(user.getPassword(), result.getPassword());
            assertEquals(user.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("маппинг к дто с параметром null")
    void toDtoNullTest() {
        assertNull(MAPPER.toDto(null));
    }

    @Test
    @DisplayName("слияние и маппинг к entity позитивный сценарий")
    void mergeToEntityPositiveTest() {
        final UserEntity result = MAPPER.mergeToEntity(userUpdateDto, user);

        assertAll(() -> {
            assertNotEquals(userUpdateDto.getId(), result.getId());
            assertEquals(userUpdateDto.getRole(), result.getRole());
            assertEquals(userUpdateDto.getPassword(), result.getPassword());
            assertEquals(userUpdateDto.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("слияние и маппинг к entity с параметром null")
    void mergeToEntityNullTest() {
        final UserEntity result = MAPPER.mergeToEntity(null, user);

        assertAll(() -> {
            assertEquals(ONE, result.getId());
            assertEquals(ROLE_USER, result.getRole());
            assertEquals(PASSWORD, result.getPassword());
            assertEquals(ONE, result.getProfileId());
        });
    }

    @Test
    @DisplayName("маппинг к списку дто позитивный сценарий")
    void toDtoListTest() {
        final List<UserDto> result = MAPPER.toDtoList(users);

        assertAll(() -> {
            assertEquals(users.size(), result.size());
            assertEquals(users.get(0).getId(), result.get(0).getId());
            assertEquals(users.get(0).getRole(), result.get(0).getRole());
            assertEquals(users.get(0).getPassword(), result.get(0).getPassword());
            assertEquals(users.get(0).getProfileId(), result.get(0).getProfileId());
        });
    }

    @Test
    @DisplayName("маппинг к списку дто c параметром null")
    void toDtoListNullTest() {
        assertNull(MAPPER.toDtoList(null));
    }
}
