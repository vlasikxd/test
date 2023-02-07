package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;

import java.util.List;

/**
 * Сервис для {@link SuspiciousAccountTransferEntity}
 */
public interface SuspiciousAccountTransferService {

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @return {@link SuspiciousAccountTransferDto}
     */
    SuspiciousAccountTransferDto create(SuspiciousAccountTransferDto transfer);

    /**
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    SuspiciousAccountTransferDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    List<SuspiciousAccountTransferDto> readAll(List<Long> ids);

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    SuspiciousAccountTransferDto update(SuspiciousAccountTransferDto transfer, Long id);
}
