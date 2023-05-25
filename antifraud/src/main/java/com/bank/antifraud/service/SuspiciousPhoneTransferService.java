package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;

import java.util.List;

/**
 * Сервис для {@link SuspiciousPhoneTransferEntity}
 */
public interface SuspiciousPhoneTransferService {

    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    SuspiciousPhoneTransferDto create(SuspiciousPhoneTransferDto transfer);

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    SuspiciousPhoneTransferDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    List<SuspiciousPhoneTransferDto> readAll(List<Long> ids);

    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    SuspiciousPhoneTransferDto update(SuspiciousPhoneTransferDto transfer, Long id);
}
