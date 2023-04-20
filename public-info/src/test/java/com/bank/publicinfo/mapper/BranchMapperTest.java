package com.bank.publicinfo.mapper;

import com.bank.publicinfo.ParentTest;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.supplier.BranchSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BranchMapperTest extends ParentTest {

    private static BranchMapper mapper;

    private static BranchEntity branch;

    private static BranchDto branchDto;

    private static BranchDto branchUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new BranchMapperImpl();

        BranchSupplier branchSupplier = new BranchSupplier();

        branchDto = branchSupplier.getDto(ONE, SPACE, TWO, SPACE);

        branchUpdateDto = branchSupplier.getDto(ONE, SPACE, TWO, SPACE);

        branch = branchSupplier.genEntity(ONE, SPACE, TWO, SPACE);
    }

    @Test
    @DisplayName("Маппинг к entity")
    void toEntityTest() {
        final BranchEntity result = mapper.toEntity(branchDto);

        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertEquals(branchDto.getAddress(), result.getAddress());
                    assertEquals(branchDto.getPhoneNumber(), result.getPhoneNumber());
                    assertEquals(branchDto.getCity(), result.getCity());
                    assertEquals(branchDto.getStartOfWork(), result.getStartOfWork());
                    assertEquals(branchDto.getEndOfWork(), result.getEndOfWork());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг к dto")
    void toDtoTest() {
        final BranchDto result = mapper.toDto(branch);

        assertAll(
                () -> {
                    assertEquals(branchDto.getId(), result.getId());
                    assertEquals(branchDto.getAddress(), result.getAddress());
                    assertEquals(branchDto.getPhoneNumber(), result.getPhoneNumber());
                    assertEquals(branchDto.getCity(), result.getCity());
                    assertEquals(branchDto.getStartOfWork(), result.getStartOfWork());
                    assertEquals(branchDto.getEndOfWork(), result.getEndOfWork());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        final BranchEntity result = mapper.mergeToEntity(branchUpdateDto, branch);

        assertAll(
                () -> {
                    assertEquals(branch.getId(), result.getId());
                    assertEquals(branch.getAddress(), result.getAddress());
                    assertEquals(branch.getPhoneNumber(), result.getPhoneNumber());
                    assertEquals(branch.getCity(), result.getCity());
                    assertEquals(branch.getStartOfWork(), result.getStartOfWork());
                    assertEquals(branch.getEndOfWork(), result.getEndOfWork());
                }
        );
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final BranchEntity result = mapper.mergeToEntity(null, branch);

        assertAll(
                () -> {
                    assertEquals(branch.getId(), result.getId());
                    assertEquals(branch.getAddress(), result.getAddress());
                    assertEquals(branch.getPhoneNumber(), result.getPhoneNumber());
                    assertEquals(branch.getCity(), result.getCity());
                    assertEquals(branch.getStartOfWork(), result.getStartOfWork());
                    assertEquals(branch.getEndOfWork(), result.getEndOfWork());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к списку dto")
    void toDtoListTest() {

        final List<BranchDto> branches = mapper.toDtoList(
                List.of(branch)
        );

        final BranchDto result = branches.get(0);

        assertAll(
                () -> {
                    assertEquals(ONE, branches.size());
                    assertEquals(branch.getId(), result.getId());
                    assertEquals(branch.getAddress(), result.getAddress());
                    assertEquals(branch.getPhoneNumber(), result.getPhoneNumber());
                    assertEquals(branch.getCity(), result.getCity());
                    assertEquals(branch.getStartOfWork(), result.getStartOfWork());
                    assertEquals(branch.getEndOfWork(), result.getEndOfWork());
                }
        );
    }

    @Test
    @DisplayName("Маппинг к списку dto, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("Маппинг к списку dto, один из элементов списка равен null")
    void toListDtoElementNullTest() {
        final List<BranchEntity> branches = new ArrayList<>();
        branches.add(branch);
        branches.add(null);

        final List<BranchDto> actually = mapper.toDtoList(branches);
        final var zeroIndexResult = actually.get(0);

        assertAll(
                () -> {
                    assertNull(actually.get(1));
                    assertEquals(TWO, actually.size());
                    assertEquals(branch.getId(), zeroIndexResult.getId());
                    assertEquals(branch.getAddress(), zeroIndexResult.getAddress());
                    assertEquals(branch.getPhoneNumber(), zeroIndexResult.getPhoneNumber());
                    assertEquals(branch.getCity(), zeroIndexResult.getCity());
                    assertEquals(branch.getStartOfWork(), zeroIndexResult.getStartOfWork());
                    assertEquals(branch.getEndOfWork(), zeroIndexResult.getEndOfWork());
                }
        );
    }
}
