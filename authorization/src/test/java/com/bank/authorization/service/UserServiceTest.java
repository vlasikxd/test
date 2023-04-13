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
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.bank.authorization.supplier.UserSupplier.getUserDto;
import static com.bank.authorization.supplier.UserSupplier.getUser;
import static com.bank.authorization.supplier.UserSupplier.getUsers;
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
    private static UserEntity updateUser;
    private static List<UserEntity> users;
    private static final String UPDATE_USER_NOT_FOUND_EXCEPTION_MESSAGE =
            "Обновление невозможно, пользователь не найден!";
    private static final String INCORRECT_PARAMETER_EXCEPTION_MESSAGE =
            "Ошибка в переданных параметрах, пользователь(и) не найден(ы)";

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Spy
    private UserMapperImpl mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    static void setUp() {
        userDto = getUserDto(ONE, USERNAME, ROLE_USER, PASSWORD, ONE);

        userUpdateDto = getUserDto(null, USERNAME, ROLE_ADMIN, PASSWORD_ADMIN, TWO);

        user = getUser(ONE, USERNAME, ROLE_USER, PASSWORD, ONE);

        UserEntity userTwo = getUser(TWO, USERNAME, ROLE_ADMIN, PASSWORD_ADMIN, TWO);

        updateUser = getUser(ONE, USERNAME, ROLE_ADMIN, PASSWORD_ADMIN, TWO);

        users = getUsers(user, userTwo);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        repositorySaveMock();

        final UserDto result = service.save(userDto);

        assertAll(() -> {
            assertEquals(user.getId(), result.getId());
            assertEquals(user.getUsername(), result.getUsername());
            assertEquals(user.getRole(), result.getRole());
            assertEquals(user.getPassword(), result.getPassword());
            assertEquals(user.getProfileId(), result.getProfileId());
        }
        );
    }

    @Test
    @DisplayName("сохранение, негативный сценарий")
    void saveNegativeTest() {
        String message = "Entity must not be null.";
        doThrow(new IllegalArgumentException(message)).when(repository).save(any());

        final var exception = assertThrows(
                IllegalArgumentException.class, () -> service.save(null)
        );

        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() {
        repositoryFindByIdMock();

        final UserDto result = service.read(ONE);

        assertAll(() -> {
            assertEquals(user.getId(), result.getId());
            assertEquals(user.getUsername(), result.getUsername());
            assertEquals(user.getRole(), result.getRole());
            assertEquals(user.getPassword(), result.getPassword());
            assertEquals(user.getProfileId(), result.getProfileId());
        }
        );
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNoUserNegativeTest() {
        repositoryFindByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals("Пользователь с данным id не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение по null-идентификатору, негативный сценарий")
    void readNullIdNegativeTest() {
        String message = "Данный id не должен быть null!";
        doThrow(new IllegalArgumentException(message)).when(repository).findById(any());

        final var exception = assertThrows(
                IllegalArgumentException.class, () -> service.read(null)
        );

        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        repositoryFindByIdMock();
        doReturn(updateUser).when(repository).save(any());
        doReturn(userUpdateDto.getPassword()).when(passwordEncoder).encode(any());

        final UserDto result = service.update(ONE, userUpdateDto);

        assertAll(() -> {
            assertNotEquals(userUpdateDto.getId(), result.getId());
            assertEquals(userUpdateDto.getUsername(), result.getUsername());
            assertEquals(userUpdateDto.getRole().name(), result.getRole().name());
            assertEquals(userUpdateDto.getPassword(), result.getPassword());
            assertEquals(userUpdateDto.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("обновление, на вход подан dto null, позитивный сценарий")
    void updateDtoNullPositiveTest() {
        repositorySaveMock();
        repositoryFindByIdMock();

        final UserDto result = service.update(ONE, null);

        assertAll(() -> {
            assertEquals(user.getId(), result.getId());
            assertEquals(user.getUsername(), result.getUsername());
            assertEquals(user.getRole(), result.getRole());
            assertEquals(user.getPassword(), result.getPassword());
            assertEquals(user.getProfileId(), result.getProfileId());
        });
    }

    @Test
    @DisplayName("обновление несуществующего пользователя, негативный сценарий")
    void updateNoUserNegativeTest() {
        repositoryFindByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new UserDto())
        );

        assertEquals(UPDATE_USER_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("обновление null-пользователя, негативный сценарий")
    void updateNullUserNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(null, new UserDto())
        );

        assertEquals(UPDATE_USER_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() {
        doReturn(users).when(repository).findAllById(any());

        final List<UserDto> result = service.readAll(List.of(ONE, TWO));

        final UserEntity firstUserInList = users.get(0);
        final UserEntity secondUserInList = users.get(1);

        final UserDto firstUserInReadList = result.get(0);
        final UserDto secondUserInReadList = result.get(1);

        assertAll(() -> {
            assertEquals(users.size(), result.size());
            assertEquals(firstUserInList.getId(), firstUserInReadList.getId());
            assertEquals(firstUserInList.getUsername(), firstUserInReadList.getUsername());
            assertEquals(firstUserInList.getRole(), firstUserInReadList.getRole());
            assertEquals(firstUserInList.getPassword(), firstUserInReadList.getPassword());
            assertEquals(firstUserInList.getProfileId(), firstUserInReadList.getProfileId());
            assertEquals(secondUserInList.getId(), secondUserInReadList.getId());
            assertEquals(secondUserInList.getUsername(), secondUserInReadList.getUsername());
            assertEquals(secondUserInList.getRole(), secondUserInReadList.getRole());
            assertEquals(secondUserInList.getPassword(), secondUserInReadList.getPassword());
            assertEquals(secondUserInList.getProfileId(), secondUserInReadList.getProfileId());
        });
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNegativeTest() {
        repositoryFindAllByIdMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals(INCORRECT_PARAMETER_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("чтение всех c id равным null, негативный сценарий")
    void readAllNegativeTestWithNullId() {
        repositoryFindAllByIdMock();

        final List<Long> ids = new ArrayList<>(Arrays.asList(null, ONE));

        final EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(ids)
        );

        assertEquals(INCORRECT_PARAMETER_EXCEPTION_MESSAGE, exception.getMessage());
    }

    private void repositorySaveMock() {
        doReturn(user).when(repository).save(any());
    }

    private void repositoryFindByIdMock() {
        doReturn(Optional.of(user)).when(repository).findById(anyLong());
    }

    private void repositoryFindByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());
    }

    private void repositoryFindAllByIdMock() {
        doReturn(List.of(new UserEntity())).when(repository).findAllById(any());
    }
}
