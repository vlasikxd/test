package com.bank.transfer.service.impl;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.mapper.AccountTransferMapperImpl;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.validator.ReadAllValidator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountTransferServiceImplTest {

    @InjectMocks
    AccountTransferServiceImpl service;

    @Mock
    AccountTransferRepository repository;

    @Spy
    AccountTransferMapper mapper = new AccountTransferMapperImpl();

    @Mock
    ReadAllValidator validator;

    static AccountTransferDto transferDto;
    static AccountTransferEntity transferEntity;
    static final Long id = 14L;
    static List<Long> ids;
    static List<AccountTransferEntity> transferList;



    @BeforeAll
    public static void init(){

        transferDto = new AccountTransferDto(14L,14L, BigDecimal.valueOf(14)
                ,"Ramzan", 14L);
        transferEntity = new AccountTransferEntity(14L, 14L, 14L, BigDecimal.valueOf(14)
                ,"Ramzan");
        ids = new ArrayList<>();
        ids.add(id);
        transferList = new ArrayList<>();
        transferList.add(transferEntity);
    }

    @Test
    @DisplayName("Позитивный сценарий добавления транзакции")
    void createTest() {

        when(repository.save(ArgumentMatchers.any())).thenReturn(transferEntity);

        AccountTransferDto accountTransferDto =  service.create(transferDto);

        assertAll(() -> Assertions.assertEquals(
                        transferEntity.getAccountDetailsId(), accountTransferDto.getAccountDetailsId()),
                () -> assertEquals(transferEntity.getAccountNumber(), accountTransferDto.getAccountNumber()),
                () -> assertEquals(transferEntity.getAmount(), accountTransferDto.getAmount()),
                () -> assertEquals(transferEntity.getPurpose(), accountTransferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий добавления транзакции")
    void createNegativeTest() {
        when(repository.save(ArgumentMatchers.any())).thenThrow(new EntityNotFoundException(""));
    }

    @Test
    @DisplayName("Позитивный сценарий обновления транзакции")
    void updateTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(transferEntity));

        when(repository.save(ArgumentMatchers.any())).thenReturn(transferEntity);

        AccountTransferDto transferDto1 = service.update(transferDto, id);

        assertAll(() -> Assertions.assertEquals(
                transferDto1.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                () -> assertEquals(transferDto1.getAccountNumber(), transferDto.getAccountNumber()),
                () -> assertEquals(transferDto1.getAmount(), transferDto.getAmount()),
                () -> assertEquals(transferDto1.getPurpose(), transferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий обновления транзакции")
    void updateNegativeTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenThrow(new
                EntityNotFoundException("AccountTransfer для обновления с указанным id не найден"));
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,() ->
                service.update(transferDto, id));
        assertEquals(exception.getMessage(), "AccountTransfer для обновления с указанным id не найден");
    }

    @Test
    @DisplayName("Позитивный сценарий чтения транзакции")
    void readTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(transferEntity));

        AccountTransferDto transferDto1 = service.read(id);

        assertAll(() -> Assertions.assertEquals(
                transferDto1.getAccountDetailsId(), transferEntity.getAccountDetailsId()),
                () -> assertEquals(transferDto1.getAccountNumber(), transferEntity.getAccountNumber()),
                () -> assertEquals(transferDto1.getAmount(), transferEntity.getAmount()),
                () -> assertEquals(transferDto1.getPurpose(), transferEntity.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий чтения транзакции")
    void readNegativeTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenThrow(new
                EntityNotFoundException("AccountTransfer с указанным id не найден"));
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.update(transferDto, id));
        assertEquals(exception.getMessage(), "AccountTransfer с указанным id не найден");
    }

    @Test
    @DisplayName("Позитивный сценарий чтения коллекции транзакций")
     void readAllTest() {

        when(repository.findAllById(ArgumentMatchers.anyCollection())).thenReturn(transferList);
        List<AccountTransferDto> transferDtoList = service.readAll(ids);
        assertEquals(transferDtoList.size(), ids.size());
    }

    @Test
    @DisplayName("Негативный сценарий чтения коллекции транзакций")
    void readAllNegativeTest() {

        when(repository.findAllById(ArgumentMatchers.anyCollection())).thenThrow(new
                EntityNotFoundException("Лист содержит один и более id, по которым нет AccountTransfer"));
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.readAll(ids));
        assertEquals(exception.getMessage(), "Лист содержит один и более id, по которым нет AccountTransfer");
    }
}