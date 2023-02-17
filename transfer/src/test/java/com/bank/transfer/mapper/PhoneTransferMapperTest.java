package com.bank.transfer.mapper;

import com.bank.transfer.ParentTest;
import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class PhoneTransferMapperTest extends ParentTest {

    final PhoneTransferMapperImpl mapper = new PhoneTransferMapperImpl();

    static PhoneTransferEntity transferEntity;

    static PhoneTransferDto transferDto;

    static List<PhoneTransferEntity> transferList;


    @BeforeAll
    public static void init() {

        transferEntity = new PhoneTransferEntity(ID, ENTITY_NUMBER, ENTITY_DETAILS_ID, AMOUNT, PURPOSE);

        transferDto = new PhoneTransferDto(ID, ENTITY_NUMBER, AMOUNT, PURPOSE, ENTITY_DETAILS_ID);

        transferList = new ArrayList<>();
        transferList.add(transferEntity);
    }

    @Test
    void toEntityTest() {

        PhoneTransferEntity phoneTransferEntity = mapper.toEntity(transferDto);

        assertAll(()-> assertEquals(phoneTransferEntity.getPhoneNumber(), transferDto.getPhoneNumber()),
                ()-> assertEquals(phoneTransferEntity.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                ()-> assertEquals(phoneTransferEntity.getAmount(), transferDto.getAmount()),
                ()-> assertEquals(phoneTransferEntity.getPurpose(), transferDto.getPurpose()));
    }

    @Test
    void toDtoTest() {

        PhoneTransferDto phoneTransferDto = mapper.toDto(transferEntity);

        assertAll(()-> assertEquals(phoneTransferDto.getPhoneNumber(), transferEntity.getPhoneNumber()),
                ()-> assertEquals(phoneTransferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId()),
                ()-> assertEquals(phoneTransferDto.getAmount(), transferEntity.getAmount()),
                ()-> assertEquals(phoneTransferDto.getPurpose(), transferEntity.getPurpose()));
    }

    @Test
    void mergeToEntityTest() {

        PhoneTransferEntity mergeEntity = mapper.mergeToEntity(transferDto, transferEntity);

        assertAll(()-> assertEquals(mergeEntity.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                ()-> assertEquals(mergeEntity.getPhoneNumber(), transferDto.getPhoneNumber()),
                ()-> assertEquals(mergeEntity.getPurpose(), transferDto.getPurpose()),
                ()-> assertEquals(mergeEntity.getAmount(), transferDto.getAmount()));
    }

    @Test
    void toDtoListTest() {

        List<PhoneTransferDto> dtoList = mapper.toDtoList(transferList);

        assertAll(()-> assertEquals(dtoList.get(0).getAccountDetailsId(), transferList.get(0).getAccountDetailsId()),
                () -> assertEquals(dtoList.get(0).getId(), transferList.get(0).getId()),
                ()-> assertEquals(dtoList.get(0).getPurpose(), transferList.get(0).getPurpose()),
                ()-> assertEquals(dtoList.get(0).getAmount(), transferList.get(0).getAmount()),
                ()-> assertEquals(dtoList.get(0).getPhoneNumber(), transferList.get(0).getPhoneNumber()));
    }
}