package com.bank.transfer.service;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;

import java.util.List;

/**
 * Сервис для {@link AccountTransferDto}
 */
public interface AccountService {

    /**
     * @param transfer {@link AccountTransferDto}
     * @return {@link AccountTransferDto}
     */
    AccountTransferDto create(AccountTransferDto transfer);

    /**
     * @param transfer {@link AccountTransferDto}
     * @param id технический идентификатор {@link AccountTransferEntity}
     * @return {@link AccountTransferDto}
     */
    AccountTransferDto update(AccountTransferDto transfer, Long id);

    /**
     * @param id технический идентификатор {@link AccountTransferEntity}
     * @return {@link AccountTransferDto}
     */
    AccountTransferDto read(Long id);

    /**
     * @param ids лист технических идентификаторов {@link AccountTransferEntity}
     * @return {@link AccountTransferDto}
     */
    List<AccountTransferDto> readAll(List<Long> ids);
}
