package com.bank.transfer.service.impl;


import com.bank.transfer.ParentTest;
import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.mapper.PhoneTransferMapperImpl;
import com.bank.transfer.repository.PhoneTransferRepository;
import com.bank.transfer.validator.ReadAllValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PhoneTransferServiceImplTest extends ParentTest {

    private static PhoneTransferDto transferDto;

    private static PhoneTransferEntity transferEntity;

    private static List<Long> ids;

    private static List<PhoneTransferEntity> transferList;

    @InjectMocks
    private PhoneTransferServiceImpl service;

    @Mock
    private PhoneTransferRepository repository;

    @Spy
    private PhoneTransferMapper mapper = new PhoneTransferMapperImpl();

    @Mock
    private ReadAllValidator validator;

    @BeforeAll
    public static void init(){

        transferDto = new PhoneTransferDto(ID, ENTITY_NUMBER, AMOUNT, PURPOSE, ENTITY_DETAILS_ID);

        transferEntity = new PhoneTransferEntity(ID, ENTITY_NUMBER, ENTITY_DETAILS_ID, AMOUNT, PURPOSE);

        ids = List.of(ID);

        transferList = List.of(transferEntity);
    }

    @Test
    @DisplayName("Позитивный сценарий создания транзакции")
    void createTest() {

        when(repository.save(ArgumentMatchers.any())).thenReturn(transferEntity);

        final PhoneTransferDto phoneTransferDto =  service.create(transferDto);

        assertAll(() -> Assertions.assertEquals(
                        transferEntity.getAccountDetailsId(), phoneTransferDto.getAccountDetailsId()),
                () -> assertEquals(transferEntity.getPhoneNumber(), phoneTransferDto.getPhoneNumber()),
                () -> assertEquals(transferEntity.getAmount(), phoneTransferDto.getAmount()),
                () -> assertEquals(transferEntity.getPurpose(), phoneTransferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий добавления транзакции")
    void createNegativeTest() {

        when(repository.save(ArgumentMatchers.any())).thenThrow(new EntityNotFoundException("Неверные данные"));

        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,() ->
                service.create(transferDto));

        assertEquals(exception.getMessage(), "Неверные данные");
    }

    @Test
    @DisplayName("Позитивный сценарий обновление транзакции")
    void updateTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(transferEntity));

        when(repository.save(ArgumentMatchers.any())).thenReturn(transferEntity);

        final PhoneTransferDto transferDto1 = service.update(transferDto, ID);

        assertAll(() -> Assertions.assertEquals(
                        transferDto1.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                () -> assertEquals(transferDto1.getPhoneNumber(), transferDto.getPhoneNumber()),
                () -> assertEquals(transferDto1.getAmount(), transferDto.getAmount()),
                () -> assertEquals(transferDto1.getPurpose(), transferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий обновления транзакции")
    void updateNegativeTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenThrow(new
                EntityNotFoundException("PhoneTransfer для обновления с указанным id не найден"));

        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,() ->
                service.update(transferDto, ID));

        assertEquals(exception.getMessage(), "PhoneTransfer для обновления с указанным id не найден");
    }

    @Test
    @DisplayName("Позитивный сценарий чтения транзакции")
    void readTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(transferEntity));

        final PhoneTransferDto transferDto1 = service.read(ID);

        assertAll(() -> Assertions.assertEquals(
                        transferDto1.getAccountDetailsId(), transferEntity.getAccountDetailsId()),
                () -> assertEquals(transferDto1.getPhoneNumber(), transferEntity.getPhoneNumber()),
                () -> assertEquals(transferDto1.getAmount(), transferEntity.getAmount()),
                () -> assertEquals(transferDto1.getPurpose(), transferEntity.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий чтения транзакции")
    void readNegativeTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenThrow(new
                EntityNotFoundException("PhoneTransfer с указанным id не найден"));

        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.update(transferDto, ID));

        assertEquals(exception.getMessage(), "PhoneTransfer с указанным id не найден");
    }

    @Test
    @DisplayName("Позитивный сценарий чтения коллекции транзакций")
    void readAllTest() {

        when(repository.findAllById(ArgumentMatchers.anyCollection())).thenReturn(transferList);

        final List<PhoneTransferDto> transferDtoList = service.readAll(ids);

        assertEquals(transferDtoList.size(), ids.size());
    }

    @Test
    @DisplayName("Негативный сценарий чтения коллекции транзакций")
    void readAllNegativeTest() {

        when(repository.findAllById(ArgumentMatchers.anyCollection())).thenThrow(new
                EntityNotFoundException("Лист содержит один и более id, по которым нет PhoneTransfer"));

        final EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.readAll(ids));

        assertEquals(exception.getMessage(), "Лист содержит один и более id, по которым нет PhoneTransfer");
    }
}