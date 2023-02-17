package com.bank.transfer.mapper;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountTransferMapperTest {

    final AccountTransferMapperImpl mapper = new AccountTransferMapperImpl();

    static AccountTransferEntity transferEntity;
    static AccountTransferDto transferDto;
    static List<AccountTransferEntity> transferList;


    @BeforeAll
    public static void init() {
        transferEntity = new AccountTransferEntity(15L,15L,15L, BigDecimal.valueOf(15)
                ,"Tarzan");
        transferDto = new AccountTransferDto(14L,14L, BigDecimal.valueOf(14)
                ,"Tarzan", 14L);
        transferList = new ArrayList<>();
        transferList.add(transferEntity);
    }

    @Test
    void toEntityTest() {
        AccountTransferEntity accountTransferEntity = mapper.toEntity(transferDto);

        assertAll(() -> assertEquals(accountTransferEntity.getAccountNumber(), transferDto.getAccountNumber()),
                () -> assertEquals(accountTransferEntity.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                () -> assertEquals(accountTransferEntity.getAmount(), transferDto.getAmount()),
                () -> assertEquals(accountTransferEntity.getPurpose(), transferDto.getPurpose()));
    }

    @Test
    void toDtoTest() {
        AccountTransferDto accountTransferDto = mapper.toDto(transferEntity);

        assertAll(() -> assertEquals(accountTransferDto.getAccountNumber(), transferEntity.getAccountNumber()),
                () -> assertEquals(accountTransferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId()),
                () -> assertEquals(accountTransferDto.getAmount(), transferEntity.getAmount()),
                () -> assertEquals(accountTransferDto.getPurpose(), transferEntity.getPurpose()));
    }

    @Test
    void mergeToEntityTest() {
        AccountTransferEntity mergeEntity = mapper.mergeToEntity(transferDto, transferEntity);

        assertAll(() -> assertEquals(mergeEntity.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                () -> assertEquals(mergeEntity.getAccountNumber(), transferDto.getAccountNumber()),
                () -> assertEquals(mergeEntity.getPurpose(), transferDto.getPurpose()),
                () -> assertEquals(mergeEntity.getAmount(), transferDto.getAmount()));
    }

    @Test
    void toDtoListTest() {
        List<AccountTransferDto> dtoList = mapper.toDtoList(transferList);

        assertAll(() -> assertEquals(dtoList.get(0).getAccountDetailsId(), transferList.get(0).getAccountDetailsId()),
                () -> assertEquals(dtoList.get(0).getId(), transferList.get(0).getId()),
                () -> assertEquals(dtoList.get(0).getPurpose(), transferList.get(0).getPurpose()),
                () -> assertEquals(dtoList.get(0).getAmount(), transferList.get(0).getAmount()),
                () -> assertEquals(dtoList.get(0).getAccountNumber(), transferList.get(0).getAccountNumber()));
    }
}