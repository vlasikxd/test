package com.bank.publicinfo.service;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.mapper.BranchMapperImpl;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.impl.BranchServiceImpl;
import com.bank.publicinfo.supplier.BranchSupplier;
import com.bank.publicinfo.validator.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
public class BranchServiceTest extends ParentTest {

    private static BranchEntity branch;

    private static BranchEntity updateBranch;

    private static BranchDto updateBranchDto;

    @InjectMocks
    private BranchServiceImpl service;

    @Mock
    private BranchRepository repository;
    @Spy
    private BranchMapperImpl mapper;
    @Spy
    private Validator validator;

    @BeforeAll
    static void init() {
        BranchSupplier branchSupplier = new BranchSupplier();

        branch = branchSupplier.genEntity(ONE, SPACE, TWO, SPACE);

        updateBranch = branchSupplier.genEntity(ONE, SPACE, TWO, SPACE);

        updateBranchDto = branchSupplier.getDto(ONE, SPACE, TWO, SPACE);
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        saveMock();

        final BranchDto result = service.save(updateBranchDto);

        assertAll(
                () -> {
                    assertEquals(updateBranchDto.getId(), result.getId());
                    assertEquals(updateBranchDto.getAddress(), result.getAddress());
                    assertEquals(updateBranchDto.getPhoneNumber(), result.getPhoneNumber());
                    assertEquals(updateBranchDto.getCity(), result.getCity());
                    assertEquals(updateBranchDto.getStartOfWork(), result.getStartOfWork());
                    assertEquals(updateBranchDto.getEndOfWork(), result.getEndOfWork());
                }
        );
    }

    @Test
    @DisplayName("сохранение, негативный сценарий")
    void saveNegativeTest() {
        doThrow(new IllegalArgumentException("Недопустимые параметры")).when(repository).save(any());

        final var exception = assertThrows(
                IllegalArgumentException.class, () -> service.save(null)
        );

        assertEquals("Недопустимые параметры", exception.getMessage());
    }

    @Test
    @DisplayName("чтение, позитивный сценарий")
    void readPositiveTest() {
        findByIdMock();

        final BranchDto result = service.read(ONE);

        assertAll(
                () -> {
                    assertEquals(branch.getId(), result.getId());
                    assertEquals(branch.getCity(), result.getCity());
                    assertEquals(branch.getAddress(), result.getAddress());
                    assertEquals(branch.getEndOfWork(), result.getEndOfWork());
                    assertEquals(branch.getPhoneNumber(), result.getPhoneNumber());
                    assertEquals(branch.getStartOfWork(), result.getStartOfWork());
                }
        );
    }

    @Test
    @DisplayName("чтение, негативный сценарий")
    void readNegativeTest() {
        findByIdEmptyMock();

        final EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> service.read(ONE)
        );

        assertEquals("branch с данным идентификатором не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        saveMock();
        findByIdMock();

        final BranchDto result = service.update(ONE, updateBranchDto);

        assertAll(
                () -> {
                    assertEquals(updateBranchDto.getId(), result.getId());
                    assertEquals(updateBranchDto.getCity(), result.getCity());
                    assertEquals(updateBranchDto.getAddress(), result.getAddress());
                    assertEquals(updateBranchDto.getEndOfWork(), result.getEndOfWork());
                    assertEquals(updateBranchDto.getStartOfWork(), result.getStartOfWork());
                    assertEquals(updateBranchDto.getPhoneNumber(), result.getPhoneNumber());
                }
        );
    }

    @Test
    @DisplayName("обновление, на вход подан dto равный null, позитивный сценарий")
    void updateDtoNullPositiveTest() {
        doReturn(branch).when(repository).save(any());
        findByIdMock();

        final BranchDto result = service.update(ONE, null);

        assertAll(
                () -> {
                    assertEquals(branch.getId(), result.getId());
                    assertEquals(branch.getCity(), result.getCity());
                    assertEquals(branch.getAddress(), result.getAddress());
                    assertEquals(branch.getEndOfWork(), result.getEndOfWork());
                    assertEquals(branch.getPhoneNumber(), result.getPhoneNumber());
                    assertEquals(branch.getStartOfWork(), result.getStartOfWork());
                }
        );
    }

    @Test
    @DisplayName("обновление, негативный сценарий")
    void updateNegativeTest() {
        findByIdEmptyMock();

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.update(ONE, new BranchDto())
        );

        assertEquals("Обновление невозможно, branch не найден!", exception.getMessage());
    }

    @Test
    @DisplayName("чтение по списку id, позитивный сценарий")
    void readAllPositiveTest() {
        final List<BranchDto> branches = readAllTestPrepare();

        final var zeroBranch = branches.get(0);
        final var firstBranch = branches.get(1);

        assertAll(
                () -> {
                    assertEquals(TWO, branches.size());
                    assertEquals(branch.getId(), zeroBranch.getId());
                    assertEquals(branch.getId(), firstBranch.getId());
                    assertEquals(updateBranch.getCity(), zeroBranch.getCity());
                    assertEquals(updateBranch.getCity(), firstBranch.getCity());
                    assertEquals(updateBranch.getAddress(), zeroBranch.getAddress());
                    assertEquals(updateBranch.getAddress(), firstBranch.getAddress());
                    assertEquals(updateBranch.getEndOfWork(), zeroBranch.getEndOfWork());
                    assertEquals(updateBranch.getEndOfWork(), firstBranch.getEndOfWork());
                    assertEquals(updateBranch.getPhoneNumber(), zeroBranch.getPhoneNumber());
                    assertEquals(updateBranch.getPhoneNumber(), firstBranch.getPhoneNumber());
                    assertEquals(updateBranch.getStartOfWork(), zeroBranch.getStartOfWork());
                    assertEquals(updateBranch.getStartOfWork(), firstBranch.getStartOfWork());
                }
        );
    }

    private List<BranchDto> readAllTestPrepare() {
        doReturn(List.of(branch, updateBranch)).when(repository).findAllById(any());

        return service.readAll(List.of(ONE, TWO));
    }

    @Test
    @DisplayName("чтение по списку id, негативный сценарий")
    void readAllNegativeTest() {
        doReturn(List.of(new BranchEntity())).when(repository).findAllById(any());

        final var exception = assertThrows(
                EntityNotFoundException.class, () -> service.readAll(List.of(ONE, TWO))
        );

        assertEquals("Ошибка в переданных параметрах, branch не существуют(ет)", exception.getMessage());
    }

    private void saveMock() {
        doReturn(updateBranch).when(repository).save(any());
    }

    private void findByIdMock() {
        doReturn(Optional.of(branch)).when(repository).findById(ONE);
    }

    private void findByIdEmptyMock() {
        doReturn(Optional.empty()).when(repository).findById(ONE);
    }
}
