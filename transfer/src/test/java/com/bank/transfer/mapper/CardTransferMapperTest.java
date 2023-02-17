package com.bank.transfer.mapper;

import com.bank.transfer.ParentTest;
import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class CardTransferMapperTest extends ParentTest {

    final CardTransferMapperImpl mapper = new CardTransferMapperImpl();

    static CardTransferEntity transferEntity;

    static CardTransferDto transferDto;

    static List<CardTransferEntity> transferList;

    @BeforeAll
    public static void init() {

        transferEntity = new CardTransferEntity(ID, ENTITY_NUMBER, ENTITY_DETAILS_ID, AMOUNT, PURPOSE);

        transferDto = new CardTransferDto(ID, ENTITY_NUMBER, AMOUNT, PURPOSE, ENTITY_DETAILS_ID);

        transferList = new ArrayList<>();
        transferList.add(transferEntity);
    }

    @Test
    void toEntityTest() {

        CardTransferEntity cardTransferEntity = mapper.toEntity(transferDto);

        assertAll(()-> assertEquals(cardTransferEntity.getCardNumber(), transferDto.getCardNumber()),
                ()-> assertEquals(cardTransferEntity.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                ()-> assertEquals(cardTransferEntity.getAmount(), transferDto.getAmount()),
                ()-> assertEquals(cardTransferEntity.getPurpose(), transferDto.getPurpose()));
    }

    @Test
    void toDtoTest() {

        CardTransferDto cardTransferDto = mapper.toDto(transferEntity);

        assertAll(()-> assertEquals(cardTransferDto.getCardNumber(), transferEntity.getCardNumber()),
                ()-> assertEquals(cardTransferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId()),
                ()-> assertEquals(cardTransferDto.getAmount(), transferEntity.getAmount()),
                ()-> assertEquals(cardTransferDto.getPurpose(), transferEntity.getPurpose()));
    }

    @Test
    void mergeToEntityTest() {

        CardTransferEntity mergeEntity = mapper.mergeToEntity(transferDto, transferEntity);

        assertAll(()-> assertEquals(mergeEntity.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                ()-> assertEquals(mergeEntity.getCardNumber(), transferDto.getCardNumber()),
                ()-> assertEquals(mergeEntity.getPurpose(), transferDto.getPurpose()),
                ()-> assertEquals(mergeEntity.getAmount(), transferDto.getAmount()));
    }

    @Test
    void toDtoListTest() {

        List<CardTransferDto> dtoList = mapper.toDtoList(transferList);

        assertAll(()-> assertEquals(dtoList.get(0).getAccountDetailsId(), transferList.get(0).getAccountDetailsId()),
                () -> assertEquals(dtoList.get(0).getId(), transferList.get(0).getId()),
                ()-> assertEquals(dtoList.get(0).getPurpose(), transferList.get(0).getPurpose()),
                ()-> assertEquals(dtoList.get(0).getAmount(), transferList.get(0).getAmount()),
                ()-> assertEquals(dtoList.get(0).getCardNumber(), transferList.get(0).getCardNumber()));
    }
}