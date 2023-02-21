package com.bank.profile.service;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapperImpl;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.imp.PassportServiceImp;
import com.bank.profile.supplier.PassportSupplier;
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

public class PassportServiceTest extends ParentTest {

    private static PassportEntity passport;
    private static PassportEntity updatedPassport;
    private static PassportDto updatedPassportDto;

    @Mock
    private PassportRepository repository;

    @InjectMocks
    private PassportServiceImp service;

    @Spy
    private PassportMapperImpl mapper;
    @Spy
    private EntityListValidator validator;

    @BeforeAll
    static void init() {
        PassportSupplier passportSupplier = new PassportSupplier();

        passport = passportSupplier.getEntity(ONE, INT_ONE, ONE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE,
                LOCAL_DATE, WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null);

        updatedPassport = passportSupplier.getEntity(null, INT_ONE, TWO, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, LOCAL_DATE, WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null);

        updatedPassportDto = passportSupplier.getDto(null, INT_ONE, TWO, WHITESPACE, WHITESPACE, WHITESPACE,
                WHITESPACE, LOCAL_DATE, WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, null);
    }

    @Test
    @DisplayName("сохранение позитивный сценарий")
    void saveTest() {
        repositorySaveMock();

        final PassportDto result = service.save(updatedPassportDto);

        assertAll(() -> {
            assertNull(result.getRegistration());
            assertEquals(updatedPassport.getId(), result.getId());
            assertEquals(updatedPassportDto.getSeries(), result.getSeries());
            assertEquals(updatedPassportDto.getNumber(), result.getNumber());
            assertEquals(updatedPassportDto.getGender(), result.getGender());
            assertEquals(updatedPassportDto.getLastName(), result.getLastName());
            assertEquals(updatedPassportDto.getIssuedBy(), result.getIssuedBy());
            assertEquals(updatedPassportDto.getFirstName(), result.getFirstName());
            assertEquals(updatedPassportDto.getBirthDate(), result.getBirthDate());
            assertEquals(updatedPassportDto.getMiddleName(), result.getMiddleName());
            assertEquals(updatedPassportDto.getBirthPlace(), result.getBirthPlace());
            assertEquals(updatedPassportDto.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(updatedPassportDto.getDivisionCode(), result.getDivisionCode());
            assertEquals(updatedPassportDto.getExpirationDate(), result.getExpirationDate());
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

        final PassportDto result = service.read(ONE);

        assertAll(() -> {
            assertNull(passport.getRegistration());
            assertEquals(passport.getId(), result.getId());
            assertEquals(passport.getSeries(), result.getSeries());
            assertEquals(passport.getNumber(), result.getNumber());
            assertEquals(passport.getGender(), result.getGender());
            assertEquals(passport.getLastName(), result.getLastName());
            assertEquals(passport.getIssuedBy(), result.getIssuedBy());
            assertEquals(passport.getFirstName(), result.getFirstName());
            assertEquals(passport.getBirthDate(), result.getBirthDate());
            assertEquals(passport.getMiddleName(), result.getMiddleName());
            assertEquals(passport.getBirthPlace(), result.getBirthPlace());
            assertEquals(passport.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(passport.getDivisionCode(), result.getDivisionCode());
            assertEquals(passport.getExpirationDate(), result.getExpirationDate());
        });
    }

    @Test
    @DisplayName("чтение негативный сценарий")
    void readNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.read(ONE));

        assertEquals("passport с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("обновление позитивный сценарий")
    void updateTest() {
        repositorySaveMock();
        repositoryFindByIdMock();

        final PassportDto result = service.update(ONE, updatedPassportDto);

        assertAll(() -> {
            assertNull(result.getRegistration());
            assertEquals(updatedPassport.getId(), result.getId());
            assertEquals(updatedPassportDto.getSeries(), result.getSeries());
            assertEquals(updatedPassportDto.getNumber(), result.getNumber());
            assertEquals(updatedPassportDto.getGender(), result.getGender());
            assertEquals(updatedPassportDto.getLastName(), result.getLastName());
            assertEquals(updatedPassportDto.getIssuedBy(), result.getIssuedBy());
            assertEquals(updatedPassportDto.getFirstName(), result.getFirstName());
            assertEquals(updatedPassportDto.getBirthDate(), result.getBirthDate());
            assertEquals(updatedPassportDto.getMiddleName(), result.getMiddleName());
            assertEquals(updatedPassportDto.getBirthPlace(), result.getBirthPlace());
            assertEquals(updatedPassportDto.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(updatedPassportDto.getDivisionCode(), result.getDivisionCode());
            assertEquals(updatedPassportDto.getExpirationDate(), result.getExpirationDate());
        });
    }

    @Test
    @DisplayName("обновление, где dto равен null")
    void updateWithIdAndNullTest() {
        doReturn(passport).when(repository).save(any());
        repositoryFindByIdMock();

        final PassportDto result = service.update(ONE, null);

        assertAll(() -> {
            assertNull(passport.getRegistration());
            assertEquals(passport.getId(), result.getId());
            assertEquals(passport.getSeries(), result.getSeries());
            assertEquals(passport.getNumber(), result.getNumber());
            assertEquals(passport.getGender(), result.getGender());
            assertEquals(passport.getLastName(), result.getLastName());
            assertEquals(passport.getIssuedBy(), result.getIssuedBy());
            assertEquals(passport.getFirstName(), result.getFirstName());
            assertEquals(passport.getBirthDate(), result.getBirthDate());
            assertEquals(passport.getMiddleName(), result.getMiddleName());
            assertEquals(passport.getBirthPlace(), result.getBirthPlace());
            assertEquals(passport.getDateOfIssue(), result.getDateOfIssue());
            assertEquals(passport.getDivisionCode(), result.getDivisionCode());
            assertEquals(passport.getExpirationDate(), result.getExpirationDate());
        });
    }

    @Test
    @DisplayName("обновление негативный сценарий")
    void updateNegativeTest() {
        repositoryFindByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(ONE, new PassportDto())
        );

        assertEquals("Обновление невозможно, passport не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id позитивный сценарий")
    void readAllTest() {
        final List<PassportDto> passports = readAllTestPrepare();
        final var zeroPassport = passports.get(0);
        final var firstPassport = passports.get(1);

        assertAll(() -> {
            assertEquals(TWO, passports.size());
            assertNull(zeroPassport.getRegistration());
            assertEquals(passport.getId(), zeroPassport.getId());
            assertEquals(passport.getSeries(), zeroPassport.getSeries());
            assertEquals(passport.getNumber(), zeroPassport.getNumber());
            assertEquals(passport.getGender(), zeroPassport.getGender());
            assertEquals(passport.getLastName(), zeroPassport.getLastName());
            assertEquals(passport.getIssuedBy(), zeroPassport.getIssuedBy());
            assertEquals(passport.getFirstName(), zeroPassport.getFirstName());
            assertEquals(passport.getBirthDate(), zeroPassport.getBirthDate());
            assertEquals(passport.getMiddleName(), zeroPassport.getMiddleName());
            assertEquals(passport.getBirthPlace(), zeroPassport.getBirthPlace());
            assertEquals(passport.getDateOfIssue(), zeroPassport.getDateOfIssue());
            assertEquals(passport.getDivisionCode(), zeroPassport.getDivisionCode());
            assertEquals(passport.getExpirationDate(), zeroPassport.getExpirationDate());
            assertNull(firstPassport.getRegistration());
            assertEquals(updatedPassport.getId(), firstPassport.getId());
            assertEquals(updatedPassport.getSeries(), firstPassport.getSeries());
            assertEquals(updatedPassport.getNumber(), firstPassport.getNumber());
            assertEquals(updatedPassport.getGender(), firstPassport.getGender());
            assertEquals(updatedPassport.getLastName(), firstPassport.getLastName());
            assertEquals(updatedPassport.getIssuedBy(), firstPassport.getIssuedBy());
            assertEquals(updatedPassport.getFirstName(), firstPassport.getFirstName());
            assertEquals(updatedPassport.getBirthDate(), firstPassport.getBirthDate());
            assertEquals(updatedPassport.getMiddleName(), firstPassport.getMiddleName());
            assertEquals(updatedPassport.getBirthPlace(), firstPassport.getBirthPlace());
            assertEquals(updatedPassport.getDateOfIssue(), firstPassport.getDateOfIssue());
            assertEquals(updatedPassport.getDivisionCode(), firstPassport.getDivisionCode());
            assertEquals(updatedPassport.getExpirationDate(), firstPassport.getExpirationDate());
        });
    }

    private List<PassportDto> readAllTestPrepare() {

        doReturn(List.of(passport, updatedPassport))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение всех негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new PassportEntity())).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, passport не существуют(ет)", exception.getMessage());
    }

    private void repositorySaveMock() {
        doReturn(updatedPassport).when(repository).save(any());
    }

    private void repositoryFindByIdMock() {
        doReturn(Optional.of(passport)).when(repository).findById(ONE);
    }

    private void repositoryFindByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
