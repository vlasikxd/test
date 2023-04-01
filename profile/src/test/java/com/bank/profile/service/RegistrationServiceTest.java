package com.bank.profile.service;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapperImpl;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.imp.RegistrationServiceImp;
import com.bank.profile.supplier.RegistrationSupplier;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class RegistrationServiceTest extends ParentTest {

    private static RegistrationEntity registration;
    private static RegistrationEntity updatedRegistration;
    private static RegistrationDto updatedRegistrationDto;

    @Mock
    private RegistrationRepository repository;

    @InjectMocks
    private RegistrationServiceImp service;

    @Spy
    private RegistrationMapperImpl mapper;
    @Spy
    private EntityListValidator validator;
    @Spy
    private DtoValidator<RegistrationDto> dtoValidator;

    @BeforeAll
    static void init() {
        RegistrationSupplier registrationSupplier = new RegistrationSupplier();

        registration = registrationSupplier.getEntity(ONE, WHITESPACE, NUMBER);

        updatedRegistration = registrationSupplier.getEntity(ONE, WHITESPACE, NUMBER);

        updatedRegistrationDto = registrationSupplier.getDto(ONE, WHITESPACE, NUMBER);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final RegistrationDto result = service.save(updatedRegistrationDto);

        assertAll(
                () -> {
                    assertEquals(updatedRegistration.getId(), result.getId());
                    assertEquals(updatedRegistrationDto.getCity(), result.getCity());
                    assertEquals(updatedRegistrationDto.getIndex(), result.getIndex());
                    assertEquals(updatedRegistrationDto.getRegion(), result.getRegion());
                    assertEquals(updatedRegistrationDto.getStreet(), result.getStreet());
                    assertEquals(updatedRegistrationDto.getCountry(), result.getCountry());
                    assertEquals(updatedRegistrationDto.getDistrict(), result.getDistrict());
                    assertEquals(updatedRegistrationDto.getLocality(), result.getLocality());
                    assertEquals(updatedRegistrationDto.getHouseBlock(), result.getHouseBlock());
                    assertEquals(updatedRegistrationDto.getFlatNumber(), result.getFlatNumber());
                    assertEquals(updatedRegistrationDto.getHouseNumber(), result.getHouseNumber());
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

        final RegistrationDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertEquals(registration.getId(), result.getId());
                    assertEquals(registration.getCity(), result.getCity());
                    assertEquals(registration.getIndex(), result.getIndex());
                    assertEquals(registration.getRegion(), result.getRegion());
                    assertEquals(registration.getStreet(), result.getStreet());
                    assertEquals(registration.getCountry(), result.getCountry());
                    assertEquals(registration.getDistrict(), result.getDistrict());
                    assertEquals(registration.getLocality(), result.getLocality());
                    assertEquals(registration.getHouseBlock(), result.getHouseBlock());
                    assertEquals(registration.getFlatNumber(), result.getFlatNumber());
                    assertEquals(registration.getHouseNumber(), result.getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("чтение, id равен null, негативный сценарий")
    void readIdNullNegativeTest() {
        final String exceptionMessage = "registration с данным id не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(null)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNotExistIdNegativeTest() {
        final String exceptionMessage = "registration с данным id не найден!";
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

        final RegistrationDto result = service.update(ONE, updatedRegistrationDto);

        assertAll(
                () -> {
                    assertEquals(updatedRegistration.getId(), result.getId());
                    assertEquals(updatedRegistrationDto.getCity(), result.getCity());
                    assertEquals(updatedRegistrationDto.getIndex(), result.getIndex());
                    assertEquals(updatedRegistrationDto.getRegion(), result.getRegion());
                    assertEquals(updatedRegistrationDto.getStreet(), result.getStreet());
                    assertEquals(updatedRegistrationDto.getCountry(), result.getCountry());
                    assertEquals(updatedRegistrationDto.getDistrict(), result.getDistrict());
                    assertEquals(updatedRegistrationDto.getLocality(), result.getLocality());
                    assertEquals(updatedRegistrationDto.getHouseBlock(), result.getHouseBlock());
                    assertEquals(updatedRegistrationDto.getFlatNumber(), result.getFlatNumber());
                    assertEquals(updatedRegistrationDto.getHouseNumber(), result.getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("обновление, id равен null, негативный сценарий")
    void updateNullIdNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, registration не найден!";
        findByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(null, updatedRegistrationDto)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("обновление, dto равен null, негативный сценарий")
    void updateNullDtoNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, registration не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, null)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("обновление несуществующих данных, негативный сценарий")
    void updateNoRegistrationNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, registration не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new RegistrationDto())
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<RegistrationDto> registrations = readAllTestPrepare();
        final var zeroRegistration = registrations.get(0);
        final var firstRegistration = registrations.get(1);

        assertAll(
                () -> {
                    assertEquals(TWO, registrations.size());
                    assertEquals(registration.getId(), zeroRegistration.getId());
                    assertEquals(registration.getCity(), zeroRegistration.getCity());
                    assertEquals(registration.getIndex(), zeroRegistration.getIndex());
                    assertEquals(registration.getRegion(), zeroRegistration.getRegion());
                    assertEquals(registration.getStreet(), zeroRegistration.getStreet());
                    assertEquals(registration.getCountry(), zeroRegistration.getCountry());
                    assertEquals(registration.getDistrict(), zeroRegistration.getDistrict());
                    assertEquals(registration.getLocality(), zeroRegistration.getLocality());
                    assertEquals(registration.getHouseBlock(), zeroRegistration.getHouseBlock());
                    assertEquals(registration.getFlatNumber(), zeroRegistration.getFlatNumber());
                    assertEquals(registration.getHouseNumber(), zeroRegistration.getHouseNumber());
                    assertEquals(updatedRegistration.getId(), firstRegistration.getId());
                    assertEquals(updatedRegistration.getCity(), firstRegistration.getCity());
                    assertEquals(updatedRegistration.getIndex(), firstRegistration.getIndex());
                    assertEquals(updatedRegistration.getRegion(), firstRegistration.getRegion());
                    assertEquals(updatedRegistration.getStreet(), firstRegistration.getStreet());
                    assertEquals(updatedRegistration.getCountry(), firstRegistration.getCountry());
                    assertEquals(updatedRegistration.getDistrict(), firstRegistration.getDistrict());
                    assertEquals(updatedRegistration.getLocality(), firstRegistration.getLocality());
                    assertEquals(updatedRegistration.getHouseBlock(), firstRegistration.getHouseBlock());
                    assertEquals(updatedRegistration.getFlatNumber(), firstRegistration.getFlatNumber());
                    assertEquals(updatedRegistration.getHouseNumber(), firstRegistration.getHouseNumber());
                }
        );
    }

    private List<RegistrationDto> readAllTestPrepare() {
        doReturn(List.of(registration, updatedRegistration))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id, id равен null, негативный сценарий")
    void readAllIdNullNegativeTest() {
        final String exceptionMessage = "Ошибка в переданных параметрах, registration не существуют(ет)";
        doReturn(List.of(new RegistrationEntity())).when(repository).findAllById(any());

        final List<Long> ids = new ArrayList<>(Arrays.asList(null, ONE));

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(ids));

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNotExistIdsNegativeTest() {
        final String exceptionMessage = "Ошибка в переданных параметрах, registration не существуют(ет)";

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    private void saveMock() {
        doReturn(updatedRegistration).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(registration)).when(repository).findById(any());
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(any());
    }
}
