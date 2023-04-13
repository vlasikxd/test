package com.bank.authorization.mapper;

import com.bank.authorization.ParentTest;
import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bank.authorization.supplier.UserSupplier.getUserDto;
import static com.bank.authorization.supplier.UserSupplier.getUser;
import static com.bank.authorization.supplier.UserSupplier.getUsers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest extends ParentTest {

    private UserMapper mapper;

    private UserEntity user;
    private UserDto userDto;
    private UserDto userUpdateDto;
    private List<UserEntity> users;

    @BeforeEach
    void setUp() {
        mapper = new UserMapperImpl();

        userDto = getUserDto(ONE, USERNAME, ROLE_USER, PASSWORD, ONE);

        userUpdateDto = getUserDto(null, USERNAME, ROLE_ADMIN, PASSWORD_ADMIN, TWO);

        user = getUser(ONE, USERNAME, ROLE_USER, PASSWORD, ONE);

        users = getUsers(user);
    }

    @Test
    @DisplayName("маппинг к entity")
    void toEntityTest() {
        final UserEntity result = mapper.toEntity(userDto);

        assertAll(() -> {
            assertNotEquals(userDto.getId(), result.getId());
            assertEquals(userDto.getUsername(), result.getUsername());
            assertEquals(userDto.getRole(), result.getRole());
            assertEquals(userDto.getPassword(), result.getPassword());
            assertEquals(userDto.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("маппинг к entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг к dto")
    void toDtoTest() {
        final UserDto result = mapper.toDto(user);

        assertAll(() -> {
            assertEquals(user.getId(), result.getId());
            assertEquals(userDto.getUsername(), result.getUsername());
            assertEquals(user.getRole(), result.getRole());
            assertEquals(user.getPassword(), result.getPassword());
            assertEquals(user.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        final UserEntity result = mapper.mergeToEntity(userUpdateDto, user);

        assertAll(() -> {
            assertNotEquals(userUpdateDto.getId(), result.getId());
            assertEquals(userUpdateDto.getUsername(), result.getUsername());
            assertEquals(userUpdateDto.getRole(), result.getRole());
            assertEquals(userUpdateDto.getPassword(), result.getPassword());
            assertEquals(userUpdateDto.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final UserEntity result = mapper.mergeToEntity(null, user);

        assertAll(() -> {
            assertEquals(ONE, result.getId());
            assertEquals(USERNAME, result.getUsername());
            assertEquals(ROLE_USER, result.getRole());
            assertEquals(PASSWORD, result.getPassword());
            assertEquals(ONE, result.getProfileId());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto")
    void toDtoListTest() {
        final List<UserDto> result = mapper.toDtoList(users);

        final UserEntity userEntity = users.get(0);
        final UserDto userDto = result.get(0);

        assertAll(() -> {
            assertEquals(users.size(), result.size());
            assertEquals(userEntity.getId(), userDto.getId());
            assertEquals(userEntity.getUsername(), userDto.getUsername());
            assertEquals(userEntity.getRole(), userDto.getRole());
            assertEquals(userEntity.getPassword(), userDto.getPassword());
            assertEquals(userEntity.getProfileId(), userDto.getProfileId());
        });
    }

    @Test
    @DisplayName("маппинг к списку dto, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("маппинг к списку dto c параметрами user и null")
    void toDtoListUserEntityAndNullTest() {
        final List<UserEntity> users = new ArrayList<>(Arrays.asList(user, null));

        final List<UserDto> result = mapper.toDtoList(users);

        final UserEntity userEntity = users.get(0);
        final UserDto userDto = result.get(0);

        assertAll(() -> {
            assertEquals(users.size(), result.size());
            assertEquals(userEntity.getId(), userDto.getId());
            assertEquals(userEntity.getUsername(), userDto.getUsername());
            assertEquals(userEntity.getRole(), userDto.getRole());
            assertEquals(userEntity.getPassword(), userDto.getPassword());
            assertEquals(userEntity.getProfileId(), userDto.getProfileId());
            assertNull(result.get(1));
        });
    }
}
