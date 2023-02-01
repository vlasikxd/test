package com.bank.transfer.service;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;

import java.util.List;

/**
 * Сервис {@link PhoneTransferDto}, {@link PhoneTransferEntity}
 */
public interface PhoneService {

    /**
     * @param transfer {@link PhoneTransferDto}
     * @return {@link PhoneTransferDto}
     */
    PhoneTransferDto create(PhoneTransferDto transfer);

    /**
     * @param transfer {@link PhoneTransferDto}
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return {@link PhoneTransferDto}
     */
    PhoneTransferDto update(PhoneTransferDto transfer, Long id);

    /**
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return {@link PhoneTransferDto}
     */
    PhoneTransferDto read(Long id);

    /**
     * @param ids лист технических идентификаторов {@link PhoneTransferEntity}
     * @return {@link List<PhoneTransferDto>}
     */
    List<PhoneTransferDto> readAll(List<Long> ids);
}
