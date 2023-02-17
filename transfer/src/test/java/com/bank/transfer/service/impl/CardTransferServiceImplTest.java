package com.bank.transfer.service.impl;

import com.bank.transfer.ParentTest;
import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.mapper.CardTransferMapper;
import com.bank.transfer.mapper.CardTransferMapperImpl;
import com.bank.transfer.repository.CardTransferRepository;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
class CardTransferServiceImplTest extends ParentTest {

    @InjectMocks
    CardTransferServiceImpl service;

    @Mock
    CardTransferRepository repository;

    @Spy
    CardTransferMapper mapper = new CardTransferMapperImpl();

    @Mock
    ReadAllValidator validator;

    static CardTransferDto transferDto;

    static CardTransferEntity transferEntity;

    static List<Long> ids;

    static List<CardTransferEntity> transferList;


    @BeforeAll
    public static void init(){

        transferDto = new CardTransferDto(ID, ENTITY_NUMBER, AMOUNT, PURPOSE, ENTITY_DETAILS_ID);

        transferEntity = new CardTransferEntity(ID, ENTITY_NUMBER, ENTITY_DETAILS_ID, AMOUNT, PURPOSE);

        ids = new ArrayList<>();
        ids.add(ID);

        transferList = new ArrayList<>();
        transferList.add(transferEntity);
    }

    @Test
    @DisplayName("Позитивный сценарий создания транзакции")
    void createTest() {

        when(repository.save(ArgumentMatchers.any())).thenReturn(transferEntity);

        CardTransferDto cardTransferDto =  service.create(transferDto);

        assertAll(() -> Assertions.assertEquals(
                        transferEntity.getAccountDetailsId(), cardTransferDto.getAccountDetailsId()),
                () -> assertEquals(transferEntity.getCardNumber(), cardTransferDto.getCardNumber()),
                () -> assertEquals(transferEntity.getAmount(), cardTransferDto.getAmount()),
                () -> assertEquals(transferEntity.getPurpose(), cardTransferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий добавления транзакции")
    void createNegativeTest() {

        when(repository.save(ArgumentMatchers.any())).thenThrow(new EntityNotFoundException("Неверные данные"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,() ->
                service.create(transferDto));

        assertEquals(exception.getMessage(), "Неверные данные");
    }

    @Test
    @DisplayName("Позитивный сценарий обновление транзакции")
    void updateTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(transferEntity));

        when(repository.save(ArgumentMatchers.any())).thenReturn(transferEntity);

        CardTransferDto transferDto1 = service.update(transferDto, ID);

        assertAll(() -> Assertions.assertEquals(
                        transferDto1.getAccountDetailsId(), transferDto.getAccountDetailsId()),
                () -> assertEquals(transferDto1.getCardNumber(), transferDto.getCardNumber()),
                () -> assertEquals(transferDto1.getAmount(), transferDto.getAmount()),
                () -> assertEquals(transferDto1.getPurpose(), transferDto.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий обновления транзакции")
    void updateNegativeTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenThrow(new
                EntityNotFoundException("CardTransfer для обновления с указанным id не найден"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,() ->
                service.update(transferDto, ID));

        assertEquals(exception.getMessage(), "CardTransfer для обновления с указанным id не найден");
    }

    @Test
    @DisplayName("Позитивный сценарий чтения транзакции")
    void readTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(transferEntity));

        CardTransferDto transferDto1 = service.read(ID);

        assertAll(() -> Assertions.assertEquals(
                        transferDto1.getAccountDetailsId(), transferEntity.getAccountDetailsId()),
                () -> assertEquals(transferDto1.getCardNumber(), transferEntity.getCardNumber()),
                () -> assertEquals(transferDto1.getAmount(), transferEntity.getAmount()),
                () -> assertEquals(transferDto1.getPurpose(), transferEntity.getPurpose()));
    }

    @Test
    @DisplayName("Негативный сценарий чтения транзакции")
    void readNegativeTest() {

        when(repository.findById(ArgumentMatchers.anyLong())).thenThrow(new
                EntityNotFoundException("CardTransfer с указанным id не найден"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.update(transferDto, ID));

        assertEquals(exception.getMessage(), "CardTransfer с указанным id не найден");
    }

    @Test
    @DisplayName("Позитивный сценарий чтения коллекции транзакций")
    void readAllTest() {

        when(repository.findAllById(ArgumentMatchers.anyCollection())).thenReturn(transferList);

        List<CardTransferDto> transferDtoList = service.readAll(ids);

        assertEquals(transferDtoList.size(), ids.size());
    }

    @Test
    @DisplayName("Негативный сценарий чтения коллекции транзакций")
    void readAllNegativeTest() {

        when(repository.findAllById(ArgumentMatchers.anyCollection())).thenThrow(new
                EntityNotFoundException("Лист содержит один и более id, по которым нет CardTransfer"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                service.readAll(ids));

        assertEquals(exception.getMessage(), "Лист содержит один и более id, по которым нет CardTransfer");
    }
}