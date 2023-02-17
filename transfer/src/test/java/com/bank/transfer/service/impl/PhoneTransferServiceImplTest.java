package com.bank.transfer.service.impl;


import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.mapper.PhoneTransferMapperImpl;
import com.bank.transfer.repository.PhoneTransferRepository;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class PhoneTransferServiceImplTest {
    @InjectMocks
    PhoneTransferServiceImpl service;

    @Mock
    PhoneTransferRepository repository;
    @Spy
    PhoneTransferMapper mapper = new PhoneTransferMapperImpl();
    @Mock
    ReadAllValidator validator;

    static PhoneTransferDto transferDto;
    static PhoneTransferEntity transferEntity;
    static final Long id = 14L;
    static List<Long> ids;
    static List<PhoneTransferEntity> transferList;


    @BeforeAll
    public static void init(){
        transferDto = new PhoneTransferDto(14L,14L, BigDecimal.valueOf(14)
                ,"Ramzan", 14L);

        transferEntity = new PhoneTransferEntity(14L, 14L, 14L, BigDecimal.valueOf(14)
                ,"Ramzan");
        ids = new ArrayList<>();
        ids.add(id);
        transferList = new ArrayList<>();
        transferList.add(transferEntity);
    }

    @Test
    @DisplayName("Позитивный сценарий создания транзакции")
    void createTest() {
        when(repository.save(ArgumentMatchers.any())).thenReturn(transferEntity);

        PhoneTransferDto phoneTransferDto =  service.create(transferDto);

        assertAll(() -> Assertions.assertEquals(
                        transferEntity.getAccountDetailsId(), phoneTransferDto.getAccountDetailsId()),
                () -> assertEquals(transferEntity.getPhoneNumber(), phoneTransferDto.getPhoneNumber()),
                () -> assertEquals(transferEntity.getAmount(), phoneTransferDto.getAmount()),
                () -> assertEquals(transferEntity.getPurpose(), phoneTransferDto.getPurpose()));
    }

    @Test
    @DisplayName("Позитивный сценарий обновление транзакции")
    void updateTest() {
        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(transferEntity));

        when(repository.save(ArgumentMatchers.any())).thenReturn(transferEntity);

        PhoneTransferDto transferDto1 = service.update(transferDto, id);

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
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,() ->
                service.update(transferDto, id));
        assertEquals(exception.getMessage(), "PhoneTransfer для обновления с указанным id не найден");
    }

    @Test
    @DisplayName("Позитивный сценарий чтения транзакции")
    void readTest() {
        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(transferEntity));

        PhoneTransferDto transferDto1 = service.read(id);

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
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.update(transferDto, id));
        assertEquals(exception.getMessage(), "PhoneTransfer с указанным id не найден");
    }

    @Test
    @DisplayName("Позитивный сценарий чтения коллекции транзакций")
    void readAllTest() {
        when(repository.findAllById(ArgumentMatchers.anyCollection())).thenReturn(transferList);
        List<PhoneTransferDto> transferDtoList = service.readAll(ids);
        assertEquals(transferDtoList.size(), ids.size());
    }

    @Test
    @DisplayName("Негативный сценарий чтения коллекции транзакций")
    void readAllNegativeTest() {
        when(repository.findAllById(ArgumentMatchers.anyCollection())).thenThrow(new
                EntityNotFoundException("Лист содержит один и более id, по которым нет PhoneTransfer"));
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.readAll(ids));
        assertEquals(exception.getMessage(), "Лист содержит один и более id, по которым нет PhoneTransfer");
    }
}