package com.bank.transfer.mapper;

import com.bank.transfer.ParentTest;
import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTransferMapperTest extends ParentTest {

    private static AccountTransferEntity transferEntity;

    private static AccountTransferDto transferDto;

    private static List<AccountTransferEntity> transferList;

    private final AccountTransferMapperImpl mapper = new AccountTransferMapperImpl();

    @BeforeAll
    public static void init() {

        transferEntity = new AccountTransferEntity(ID, ENTITY_NUMBER, ENTITY_DETAILS_ID, AMOUNT, PURPOSE);

        transferDto = new AccountTransferDto(ID, ENTITY_NUMBER, AMOUNT, PURPOSE, ENTITY_DETAILS_ID);

        transferList = List.of(transferEntity);
    }

    @Test
    void toEntityTest() {

        final AccountTransferEntity accountTransferEntity = mapper.toEntity(transferDto);

        assertAll(() -> assertEquals(accountTransferEntity.getAccountNumber(), transferDto.getAccountNumber()),
                () -> assertEquals(accountTransferEntity.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                () -> assertEquals(accountTransferEntity.getAmount(), transferDto.getAmount()),
                () -> assertEquals(accountTransferEntity.getPurpose(), transferDto.getPurpose()));
    }

    @Test
    void toDtoTest() {

        final AccountTransferDto accountTransferDto = mapper.toDto(transferEntity);

        assertAll(() -> assertEquals(accountTransferDto.getAccountNumber(), transferEntity.getAccountNumber()),
                () -> assertEquals(accountTransferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId()),
                () -> assertEquals(accountTransferDto.getAmount(), transferEntity.getAmount()),
                () -> assertEquals(accountTransferDto.getPurpose(), transferEntity.getPurpose()));
    }

    @Test
    void mergeToEntityTest() {

        final AccountTransferEntity mergeEntity = mapper.mergeToEntity(transferDto, transferEntity);

        assertAll(() -> assertEquals(mergeEntity.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                () -> assertEquals(mergeEntity.getAccountNumber(), transferDto.getAccountNumber()),
                () -> assertEquals(mergeEntity.getPurpose(), transferDto.getPurpose()),
                () -> assertEquals(mergeEntity.getAmount(), transferDto.getAmount()));
    }

    @Test
    void toDtoListTest() {

        final List<AccountTransferDto> dtoList = mapper.toDtoList(transferList);

        assertAll(() -> assertEquals(dtoList.get(0).getAccountDetailsId(), transferList.get(0).getAccountDetailsId()),
                () -> assertEquals(dtoList.get(0).getId(), transferList.get(0).getId()),
                () -> assertEquals(dtoList.get(0).getPurpose(), transferList.get(0).getPurpose()),
                () -> assertEquals(dtoList.get(0).getAmount(), transferList.get(0).getAmount()),
                () -> assertEquals(dtoList.get(0).getAccountNumber(), transferList.get(0).getAccountNumber()));
    }
}