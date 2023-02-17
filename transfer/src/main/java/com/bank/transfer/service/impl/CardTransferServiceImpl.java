package com.bank.transfer.service.impl;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.mapper.CardTransferMapper;
import com.bank.transfer.returner.EntityNotFoundReturner;
import com.bank.transfer.repository.CardTransferRepository;
import com.bank.transfer.service.CardTransferService;
import com.bank.transfer.validator.ReadAllValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация {@link CardTransferService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CardTransferServiceImpl implements CardTransferService {

    private final CardTransferRepository repository;
    private final CardTransferMapper mapper;
    private final EntityNotFoundReturner notFoundReturner;
    private final ReadAllValidator validator;

    /**
     * @param transfer {@link CardTransferDto}
     * @return  {@link CardTransferDto}
     */
    @Override
    @Transactional
    public CardTransferDto create(CardTransferDto transfer) {
        final CardTransferEntity cardTransfer = repository.save(mapper.toEntity(transfer));
        return mapper.toDto(cardTransfer);
    }

    /**
     * @param transferDto {@link CardTransferDto}
     * @param id технический идентификатор {@link CardTransferEntity}
     * @return  {@link CardTransferDto}
     */
    @Override
    @Transactional
    public CardTransferDto update(CardTransferDto transferDto, Long id) {
        final CardTransferEntity cardTransfer = repository.findById(id).
                orElseThrow(() -> {
                    throw notFoundReturner.getException("CardTransfer для обновления с указанным id не найден");
                });
        final CardTransferEntity transfer = repository.save(mapper.mergeToEntity(transferDto, cardTransfer));
        return mapper.toDto(repository.save(transfer));
    }

    /**
     * @param id технический идентификатор {@link CardTransferEntity}
     * @return  {@link CardTransferDto}
     */
    @Override
    public CardTransferDto read(Long id) {
        final CardTransferEntity cardTransfer = repository.findById(id).
                orElseThrow(() -> {
                    throw notFoundReturner.getException("CardTransfer с указанным id не найден");
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
         final String exceptionMessage = "Лист содержит один и более id, по которым нет CardTransfer";
        validator.validation(transfers, ids, exceptionMessage);
        return transfers;
    }
}
