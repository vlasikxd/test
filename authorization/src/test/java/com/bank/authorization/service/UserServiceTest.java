package com.bank.authorization.service;

import com.bank.authorization.ParentTest;
import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.mapper.UserMapperImpl;
import com.bank.authorization.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class UserServiceTest extends ParentTest {

    private static UserDto userDto;
    private static UserEntity user;
    private static UserDto userUpdateDto;
    private static UserEntity userUpdate;
    private static List<UserEntity> users;

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Spy
    private UserMapperImpl mapper;

    @BeforeAll
    static void init() {
        userDto = getUserDto(ONE, ROLE_USER, PASSWORD, ONE);

        userUpdateDto = getUserDto(null, ROLE_ADMIN, PASSWORD_ADMIN, TWO);

        user = getUser(ONE, ROLE_USER, PASSWORD, ONE);

        userUpdate = getUser(ONE, ROLE_ADMIN, PASSWORD_ADMIN, TWO);

        users = getUsers(user);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() {
        doReturn(user).when(repository).save(any());

        final UserDto result = service.save(userDto);

        assertAll(() -> {
            assertEquals(user.getId(), result.getId());
            assertEquals(user.getRole(), result.getRole());
            assertEquals(user.getPassword(), result.getPassword());
            assertEquals(user.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("сохранение негативный сценарий")
    void saveNegativeTest() {
        doThrow(new IllegalArgumentException("Недопустимые параметры")).when(repository).save(any());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.save(null)
        );

        assertEquals("Недопустимые параметры", exception.getMessage());
    }

    @Test
    @DisplayName("чтение позитивный сценарий")
    void readTest() {
        doReturn(Optional.of(user)).when(repository).findById(anyLong());

        final UserDto result = service.read(ONE);

        assertAll(() -> {
            assertEquals(user.getId(), result.getId());
            assertEquals(user.getRole(), result.getRole());
            assertEquals(user.getPassword(), result.getPassword());
            assertEquals(user.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.read(ONE));

        assertEquals("Пользователь с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() {
        doReturn(Optional.of(user)).when(repository).findById(anyLong());
        doReturn(userUpdate).when(repository).save(any());

        final UserDto result = service.update(ONE, userUpdateDto);

        assertAll(() -> {
            assertNotEquals(userUpdateDto.getId(), result.getId());
            assertEquals(userUpdateDto.getRole(), result.getRole());
            assertEquals(userUpdateDto.getPassword(), result.getPassword());
            assertEquals(userUpdateDto.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("обновление с не пустым id и null")
    void updateWithIdAndNullTest() {
        doReturn(user).when(repository).save(any());
        doReturn(Optional.of(user)).when(repository).findById(anyLong());

        final UserDto result = service.update(ONE, null);

        assertAll(() -> {
            assertEquals(user.getId(), result.getId());
            assertEquals(user.getRole(), result.getRole());
            assertEquals(user.getPassword(), result.getPassword());
            assertEquals(user.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(ONE, new UserDto())
        );

        assertEquals("Обновление невозможно, пользователь не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение всех позитивный сценарий")
    void readAllTest() {
        doReturn(users).when(repository).findAllById(any());

        final List<UserDto> result = service.readAll(List.of(ONE));

        assertAll(() -> {
            assertEquals(users.size(), result.size());
            assertEquals(users.get(0).getId(), result.get(0).getId());
            assertEquals(users.get(0).getRole(), result.get(0).getRole());
            assertEquals(users.get(0).getPassword(), result.get(0).getPassword());
            assertEquals(users.get(0).getProfileId(), result.get(0).getProfileId());
        });
    }

    @Test
    @DisplayName("чтение всех негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new UserEntity())).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, пользователи(ь) не найден(ы)", exception.getMessage());
    }
}
