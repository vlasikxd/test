package com.bank.publicinfo.service;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.mapper.AtmMapperImpl;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.service.impl.AtmServiceImpl;
import com.bank.publicinfo.supplier.AtmSupplier;
import com.bank.publicinfo.validator.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class AtmServiceTest extends ParentTest {

    private static AtmEntity atm;

    private static AtmEntity updateAtm;

    private static AtmDto updateAtmDto;

    @Mock
    AtmRepository repository;

    @InjectMocks
    private AtmServiceImpl service;

    @Spy
    private AtmMapperImpl mapper;
    @Spy
    private Validator validator;

    @BeforeAll
    static void init() {
        AtmSupplier atmSupplier = new AtmSupplier();

        atm = atmSupplier.getEntity(ONE, SPACE, TRUE, null);

        updateAtm = atmSupplier.getEntity(ONE, SPACE, TRUE, null);

        updateAtmDto = atmSupplier.getDto(ONE, SPACE, TRUE, null);
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final AtmDto result = service.save(updateAtmDto);

        assertAll(
                () -> {
                    assertNull(result.getBranch());
                    assertEquals(updateAtmDto.getId(), result.getId());
                    assertEquals(updateAtmDto.getAddress(), result.getAddress());
                    assertEquals(updateAtmDto.getStartOfWork(), result.getStartOfWork());
                    assertEquals(updateAtmDto.getEndOfWork(), result.getEndOfWork());
                    assertEquals(updateAtmDto.getAllHours(), result.getAllHours());
                }
        );
    }

    @Test
    @DisplayName("Сохранение недопустимых параметров, негативный сценарий")
    void saveInvalidParametersNegativeTest() {
        String errorMessage = "Недопустимые параметры";

        doThrow(new IllegalArgumentException(errorMessage)).when(repository).save(any());

        final var exception = assertThrows(
                IllegalArgumentException.class, () -> service.save(null)
        );

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Чтение, позитивный сценарий")
    void readPositiveTest() {
        findByIdMock();

        final AtmDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertNull(result.getBranch());
                    assertEquals(atm.getId(), result.getId());
                    assertEquals(atm.getAddress(), result.getAddress());
                    assertEquals(atm.getStartOfWork(), result.getStartOfWork());
                    assertEquals(atm.getEndOfWork(), result.getEndOfWork());
                    assertEquals(atm.getAllHours(), result.getAllHours());
                }
        );
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readNotExistIdNegativeTest() {
        findByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals("atm с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("Обновление, позитивный сценарий")
    void updatePositiveTest() {
        saveMock();
        findByIdMock();

        final AtmDto result = service.update(ONE, updateAtmDto);

        assertAll(
                () -> {
                    assertNull(result.getBranch());
                    assertEquals(updateAtmDto.getId(), result.getId());
                    assertEquals(updateAtmDto.getAddress(), result.getAddress());
                    assertEquals(updateAtmDto.getStartOfWork(), result.getStartOfWork());
                    assertEquals(updateAtmDto.getEndOfWork(), result.getEndOfWork());
                    assertEquals(updateAtmDto.getAllHours(), result.getAllHours());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан dto равный null, позитивный сценарий")
    void updateDtoNullPositiveTest() {
        doReturn(atm).when(repository).save(any());
        findByIdMock();

        final AtmDto result = service.update(ONE, null);

        assertAll(
                () -> {
                    assertNull(result.getBranch());
                    assertEquals(atm.getId(), result.getId());
                    assertEquals(atm.getAddress(), result.getAddress());
                    assertEquals(atm.getStartOfWork(), result.getStartOfWork());
                    assertEquals(atm.getEndOfWork(), result.getEndOfWork());
                    assertEquals(atm.getAllHours(), result.getAllHours());
                }
        );
    }

    @Test
    @DisplayName("Обновление несуществующего atm, негативный сценарий")
    void updateNotExistAtmNegativeTest() {
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new AtmDto())
        );

        assertEquals("Обновление невозможно, atm не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("Чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<AtmDto> atms = readAllTestPrepare();

        final var zeroAtm = atms.get(0);
        final var firstAtm = atms.get(1);

        assertAll(
                () -> {
                    assertNull(zeroAtm.getBranch());
                    assertNull(firstAtm.getBranch());
                    assertEquals(TWO, atms.size());
                    assertEquals(atm.getId(), firstAtm.getId());
                    assertEquals(atm.getId(), zeroAtm.getId());
                    assertEquals(updateAtm.getAddress(), zeroAtm.getAddress());
                    assertEquals(updateAtm.getStartOfWork(), zeroAtm.getStartOfWork());
                    assertEquals(updateAtm.getEndOfWork(), zeroAtm.getEndOfWork());
                    assertEquals(updateAtm.getAllHours(), zeroAtm.getAllHours());
                    assertEquals(updateAtm.getAddress(), firstAtm.getAddress());
                    assertEquals(updateAtm.getStartOfWork(), firstAtm.getStartOfWork());
                    assertEquals(updateAtm.getEndOfWork(), firstAtm.getEndOfWork());
                    assertEquals(updateAtm.getAllHours(), firstAtm.getAllHours());
                }
        );
    }

    private List<AtmDto> readAllTestPrepare() {
        doReturn(List.of(atm, updateAtm))
                .when(repository)
                .findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("Чтение по списку несуществующих id, негативный сценарий")
    void readAllNotExistIdNegativeTest() {
        doReturn(List.of(new AtmEntity())).when(repository).findAllById(any());

        final EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, atm не существуют(ет)", exception.getMessage());
    }

    private void saveMock() {
        doReturn(updateAtm).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(atm)).when(repository).findById(ONE);
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
