package com.bank.profile.service;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapperImpl;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.imp.ActualRegistrationServiceImp;
import com.bank.profile.supplier.ActualRegistrationSupplier;
import com.bank.profile.validator.EntityListValidator;
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

public class ActualRegistrationServiceTest extends ParentTest {

    private static ActualRegistrationEntity registration;
    private static ActualRegistrationEntity updatedRegistration;
    private static ActualRegistrationDto updatedRegistrationDto;

    @Mock
    private ActualRegistrationRepository repository;

    @InjectMocks
    private ActualRegistrationServiceImp service;

    @Spy
    private ActualRegistrationMapperImpl mapper;
    @Spy
    private EntityListValidator validator;

    @BeforeAll
    static void init() {
        ActualRegistrationSupplier actualRegistrationSupplier = new ActualRegistrationSupplier();

        registration = actualRegistrationSupplier.getEntity(ONE, WHITESPACE, TWO);

        updatedRegistration = actualRegistrationSupplier.getEntity(ONE, WHITESPACE, ONE);

        updatedRegistrationDto = actualRegistrationSupplier.getDto(ONE, WHITESPACE, ONE);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final ActualRegistrationDto result = service.save(updatedRegistrationDto);

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

        final ActualRegistrationDto result = service.read(ONE);

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
    @DisplayName("чтение, id равен null негативный сценарий")
    void readIdNullNegativeTest() {
        final String exceptionMessage = "actualRegistration с данным id не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(null)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readNotExistIdNegativeTest() {
        final String exceptionMessage = "actualRegistration с данным id не найден!";
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

        final ActualRegistrationDto result = service.update(ONE, updatedRegistrationDto);

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
        final String exceptionMessage = "Обновление невозможно, ActualRegistration не найден!";
        findByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(null, updatedRegistrationDto)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("обновление, dto равен null, позитивный сценарий")
    void updateNullDtoPositiveTest() {
        doReturn(registration).when(repository).save(any());
        findByIdMock();

        final ActualRegistrationDto result = service.update(ONE, null);

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
    @DisplayName("обновление несуществующих данных, негативный сценарий")
    void updateNoActualRegistrationNegativeTest() {
        final String exceptionMessage = "Обновление невозможно, ActualRegistration не найден!";
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new ActualRegistrationDto())
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<ActualRegistrationDto> registrations = readAllTestPrepare();
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

    private List<ActualRegistrationDto> readAllTestPrepare() {
        doReturn(List.of(registration, updatedRegistration))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id, id равен null, негативный сценарий")
    void readAllIdNullNegativeTest() {
        final String exceptionMessage = "Ошибка в переданных параметрах, ActualRegistration не существуют(ет)";
        doReturn(List.of(new ActualRegistrationEntity())).when(repository).findAllById(any());

        final List<Long> ids = new ArrayList<>(Arrays.asList(null, ONE));

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(ids)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }


    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNotExistIdsNegativeTest() {
        final String exceptionMessage = "Ошибка в переданных параметрах, ActualRegistration не существуют(ет)";

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
