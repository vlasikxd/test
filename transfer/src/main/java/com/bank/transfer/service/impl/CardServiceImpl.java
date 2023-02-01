package com.bank.transfer.service.impl;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.exception.EntityNotFoundReturner;
import com.bank.transfer.mapper.CardMapper;
import com.bank.transfer.repository.CardRepository;
import com.bank.transfer.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO Реализация {@link CardService}.
 * Реализация сервис слоя для {@link CardTransferDto}, {@link CardTransferEntity}
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final CardMapper mapper;
    private final EntityNotFoundReturner notFoundReturner;
    // TODO удали и оставь пустую строку.
    /**
     * @param transfer {@link CardTransferDto}
     * @return  {@link CardTransferDto}
     */
    @Override
    @Transactional
    public CardTransferDto create(CardTransferDto transfer) {
        // TODO entity переименуй в cardTransfer.
        CardTransferEntity entity = repository.save(mapper.toEntity(transfer));
        return mapper.toDto(entity);
    }

    /**
     * @param transfer {@link CardTransferDto}
     * @param id технический идентификатор {@link CardTransferEntity}
     * @return  {@link CardTransferDto}
     */
    @Override
    @Transactional
    // TODO transfer переименуй в transferDto.
    public CardTransferDto update(CardTransferDto transfer, Long id) {
        final CardTransferEntity cardTransfer = repository.findById(id).
                orElseThrow(() -> {
                    // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
                    throw notFoundReturner.getException("Запись с указанным id не найдена");
                });
        // TODO transferEntity переименуй в transfer. и смысл save(mapper.mergeToEntity(transfer, cardTransfer)); в этом переносе?
        final CardTransferEntity transferEntity = repository.
                save(mapper.mergeToEntity(transfer, cardTransfer));
        return mapper.toDto(repository.save(transferEntity));
    }

    /**
     * @param id технический идентификатор {@link CardTransferEntity}
     * @return  {@link CardTransferDto}
     */
    @Override
    public CardTransferDto read(Long id) {
        final CardTransferEntity cardTransfer = repository.findById(id).
                orElseThrow(() -> {
                    // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
                    throw notFoundReturner.getException("Запись с указанным id не найдена");
                });
        return mapper.toDto(cardTransfer);
    }

    /**
     * @param ids лист технических идентификаторов {@link CardTransferEntity}
     * @return  {@link List<CardTransferDto>}
     */
    @Override
    public List<CardTransferDto> readAll(List<Long> ids) {
        final List<CardTransferDto> transfers = mapper.toDtoList(repository.findAllById(ids));
        // TODO вот эта проверка(if) дублируется во всех реализациях, сделай отдельный класс и переиспользуй, как с
        //  notFoundReturner.getException
        if (ids.size() != transfers.size()) {
            // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
            throw notFoundReturner.getException("Одна или более записей из списка id не найдены");
        }
        return transfers;
    }
}
