package com.bank.profile.service;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapperImpl;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.imp.ProfileServiceImp;
import com.bank.profile.supplier.ProfileSupplier;
import com.bank.profile.validator.EntityListValidator;
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

    @BeforeAll
    static void init() {
        ProfileSupplier profileSupplier = new ProfileSupplier();

        profile = profileSupplier.getEntity(ONE, ONE, WHITESPACE, WHITESPACE, ONE, ONE, null, null);

        updatedProfile = profileSupplier.getEntity(null, TWO, WHITESPACE, WHITESPACE, TWO, TWO, null, null);

        updatedProfileDto = profileSupplier.getDto(null, TWO, WHITESPACE, WHITESPACE, TWO, TWO, null, null);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() {
        repositorySaveMock();

        final ProfileDto result = service.save(updatedProfileDto);

        assertAll(() -> {
            assertNull(result.getPassport());
            assertNull(result.getActualRegistration());
            assertEquals(updatedProfile.getId(), result.getId());
            assertEquals(updatedProfileDto.getInn(), result.getInn());
            assertEquals(updatedProfileDto.getEmail(), result.getEmail());
            assertEquals(updatedProfileDto.getSnils(), result.getSnils());
            assertEquals(updatedProfileDto.getNameOnCard(), result.getNameOnCard());
            assertEquals(updatedProfileDto.getPhoneNumber(), result.getPhoneNumber());
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
        repositoryFindByIdMock();

        final ProfileDto result = service.read(ONE);

        assertAll(() -> {
            assertNull(result.getPassport());
            assertNull(result.getActualRegistration());
            assertEquals(profile.getId(), result.getId());
            assertEquals(profile.getInn(), result.getInn());
            assertEquals(profile.getEmail(), result.getEmail());
            assertEquals(profile.getSnils(), result.getSnils());
            assertEquals(profile.getNameOnCard(), result.getNameOnCard());
            assertEquals(profile.getPhoneNumber(), result.getPhoneNumber());
        });
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.read(ONE));

        assertEquals("profile с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() {
        repositorySaveMock();
        repositoryFindByIdMock();

        final ProfileDto result = service.update(ONE, updatedProfileDto);

        assertAll(() -> {
            assertNull(result.getPassport());
            assertNull(result.getActualRegistration());
            assertEquals(updatedProfile.getId(), result.getId());
            assertEquals(updatedProfileDto.getInn(), result.getInn());
            assertEquals(updatedProfileDto.getEmail(), result.getEmail());
            assertEquals(updatedProfileDto.getSnils(), result.getSnils());
            assertEquals(updatedProfileDto.getNameOnCard(), result.getNameOnCard());
            assertEquals(updatedProfileDto.getPhoneNumber(), result.getPhoneNumber());
        });
    }

    @Test
    @DisplayName("обновление, где dto равен null")
    void updateWithIdAndNullTest() {
        doReturn(profile).when(repository).save(any());
        repositoryFindByIdMock();

        final ProfileDto result = service.update(ONE, null);

        assertAll(() -> {
            assertNull(result.getPassport());
            assertNull(result.getActualRegistration());
            assertEquals(profile.getId(), result.getId());
            assertEquals(profile.getInn(), result.getInn());
            assertEquals(profile.getEmail(), result.getEmail());
            assertEquals(profile.getSnils(), result.getSnils());
            assertEquals(profile.getNameOnCard(), result.getNameOnCard());
            assertEquals(profile.getPhoneNumber(), result.getPhoneNumber());
        });
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(ONE, new ProfileDto())
        );

        assertEquals("Обновление невозможно, profile не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id позитивный сценарий")
    void readAllTest() {
        final List<ProfileDto> profiles = readAllTestPrepare();
        final var zeroProfile = profiles.get(0);
        final var firstProfile = profiles.get(1);

        assertAll(() -> {
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
        });
    }

    private List<ProfileDto> readAllTestPrepare() {

        doReturn(List.of(profile, updatedProfile))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new ProfileEntity())).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, profile не существуют(ет)", exception.getMessage());
    }

    private void repositorySaveMock() {
        doReturn(updatedProfile).when(repository).save(any());
    }

    private void repositoryFindByIdMock() {
        doReturn(Optional.of(profile)).when(repository).findById(ONE);
    }

    private void repositoryFindByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
