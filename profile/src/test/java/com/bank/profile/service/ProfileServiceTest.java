package com.bank.profile.service;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapperImpl;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.imp.ProfileServiceImp;
import com.bank.profile.supplier.ProfileSupplier;
import com.bank.profile.validator.EntityListValidator;
import com.bank.profile.validator.DtoValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class ProfileServiceTest extends ParentTest {

    private static ProfileEntity profile;
    private static ProfileEntity updatedProfile;
    private static ProfileDto updatedProfileDto;

    @Mock
    private ProfileRepository repository;

    @InjectMocks
    private ProfileServiceImp service;

    @Spy
    private ProfileMapperImpl mapper;
    @Spy
    private EntityListValidator validator;
    @Spy
    private DtoValidator<ProfileDto> dtoValidator;

    @BeforeAll
    static void init() {
        ProfileSupplier profileSupplier = new ProfileSupplier();

        profile = profileSupplier.getEntity(ONE, PHONE_NUMBER, EMAIL, WHITESPACE);

        updatedProfile = profileSupplier.getEntity(ONE, PHONE_NUMBER, EMAIL, WHITESPACE);

        updatedProfileDto = profileSupplier.getDto(ONE, PHONE_NUMBER, EMAIL, WHITESPACE);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final ProfileDto result = service.save(updatedProfileDto);

        assertAll(
                () -> {
                    assertNull(result.getPassport());
                    assertNull(result.getActualRegistration());
                    assertEquals(updatedProfile.getId(), result.getId());
                    assertEquals(updatedProfileDto.getInn(), result.getInn());
                    assertEquals(updatedProfileDto.getEmail(), result.getEmail());
                    assertEquals(updatedProfileDto.getSnils(), result.getSnils());
                    assertEquals(updatedProfileDto.getNameOnCard(), result.getNameOnCard());
                    assertEquals(updatedProfileDto.getPhoneNumber(), result.getPhoneNumber());
                }
        );
    }

    @Test
    @DisplayName("сохранение, негативный сценарий")
    void saveNegativeTest() {
        final String exceptionMessage = "Entity must not be null";
        doThrow(new IllegalArgumentException(exceptionMessage)).when(repository).save(any());

        final var exception = assertThrows(
                IllegalArgumentException.class, () -> service.save(null)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() {
        findByIdMock();

        final ProfileDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertNull(result.getPassport());
                    assertNull(result.getActualRegistration());
                    assertEquals(profile.getId(), result.getId());
                    assertEquals(profile.getInn(), result.getInn());
                    assertEquals(profile.getEmail(), result.getEmail());
                    assertEquals(profile.getSnils(), result.getSnils());
                    assertEquals(profile.getNameOnCard(), result.getNameOnCard());
                    assertEquals(profile.getPhoneNumber(), result.getPhoneNumber());
                }
        );
    }

    @Test
    @DisplayName("чтение, id равен null, негативный сценарий")
    void readIdNullNegativeTest() {
        final String exceptionMessage = "profile с данным id не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(null)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNotExistIdNegativeTest() {
        final String exceptionMessage = "profile с данным id не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        saveMock();
        findByIdMock();

        final ProfileDto result = service.update(ONE, updatedProfileDto);

        assertAll(
                () -> {
                    assertNull(result.getPassport());
                    assertNull(result.getActualRegistration());
                    assertEquals(updatedProfile.getId(), result.getId());
                    assertEquals(updatedProfileDto.getInn(), result.getInn());
                    assertEquals(updatedProfileDto.getEmail(), result.getEmail());
                    assertEquals(updatedProfileDto.getSnils(), result.getSnils());
                    assertEquals(updatedProfileDto.getNameOnCard(), result.getNameOnCard());
                    assertEquals(updatedProfileDto.getPhoneNumber(), result.getPhoneNumber());
                }
        );
    }

    @Test
    @DisplayName("обновление, id равен null, негативный сценарий")
    void updateNullIdNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, profile не найден!";
        findByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(null, updatedProfileDto)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("обновление, dto равен null, негативный сценарий")
    void updateNullDtoNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, profile не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, null)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("обновление несуществующих данных, негативный сценарий")
    void updateNoProfileNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, profile не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new ProfileDto())
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<ProfileDto> profiles = readAllTestPrepare();
        final var zeroProfile = profiles.get(0);
        final var firstProfile = profiles.get(1);

        assertAll(
                () -> {
                    assertNull(zeroProfile.getPassport());
                    assertNull(firstProfile.getPassport());
                    assertNull(firstProfile.getActualRegistration());
                    assertNull(zeroProfile.getActualRegistration());
                    assertEquals(TWO, profiles.size());
                    assertEquals(profile.getId(), zeroProfile.getId());
                    assertEquals(profile.getInn(), zeroProfile.getInn());
                    assertEquals(profile.getEmail(), zeroProfile.getEmail());
                    assertEquals(profile.getSnils(), zeroProfile.getSnils());
                    assertEquals(profile.getNameOnCard(), zeroProfile.getNameOnCard());
                    assertEquals(profile.getPhoneNumber(), zeroProfile.getPhoneNumber());
                    assertEquals(updatedProfile.getId(), firstProfile.getId());
                    assertEquals(updatedProfile.getInn(), firstProfile.getInn());
                    assertEquals(updatedProfile.getEmail(), firstProfile.getEmail());
                    assertEquals(updatedProfile.getSnils(), firstProfile.getSnils());
                    assertEquals(updatedProfile.getNameOnCard(), firstProfile.getNameOnCard());
                    assertEquals(updatedProfile.getPhoneNumber(), firstProfile.getPhoneNumber());
                }
        );
    }

    private List<ProfileDto> readAllTestPrepare() {
        doReturn(List.of(profile, updatedProfile))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id, id равен null, негативный сценарий")
    void readAllIdNullNegativeTest() {
        final String exceptionMessage = "Ошибка в переданных параметрах, profile не существуют(ет)";
        doReturn(List.of(new ProfileEntity())).when(repository).findAllById(any());

        final List<Long> ids = new ArrayList<>(Arrays.asList(null, ONE));

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(ids));

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNotExistIdsNegativeTest() {
        final String exceptionMessage = "Ошибка в переданных параметрах, profile не существуют(ет)";

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    private void saveMock() {
        doReturn(updatedProfile).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(profile)).when(repository).findById(any());
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(any());
    }
}
