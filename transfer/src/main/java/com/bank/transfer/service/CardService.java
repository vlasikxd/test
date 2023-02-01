package com.bank.transfer.service;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;

import java.util.List;

/**
 * Сервис для {@link CardTransferDto}
 */
public interface CardService {

    /**
     * @param transfer {@link CardTransferDto}
     * @return {@link CardTransferDto}
     */
    CardTransferDto create(CardTransferDto transfer);

    /**
     * @param transfer {@link CardTransferDto}
     * @param id технический идентификатор {@link CardTransferEntity}
     * @return {@link CardTransferDto}
     */
    CardTransferDto update(CardTransferDto transfer, Long id);

    /**
     * @param id технический идентификатор {@link CardTransferEntity}
     * @return {@link CardTransferDto}
     */
    CardTransferDto read(Long id);

    /**
     * @param ids лист технических идентификаторов {@link CardTransferEntity}
     * TODO return не просто дто, а лист дто!
     * @return {@link CardTransferDto}
     */
    List<CardTransferDto> readAll(List<Long> ids);
}
