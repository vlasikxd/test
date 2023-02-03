package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;

import java.util.List;

/**
 Service для {@link AccountDetailsDto}.
 */
public interface AccountDetailsService {

    /**
     * @param id техничский идентификатор {@link AccountDetailsEntity}.
     * @return {@link AccountDetailsDto}
     */
    AccountDetailsDto readById(Long id);

    /**
     * @param ids список технических идентификаторов {@link AccountDetailsEntity}.
     * @return список {@link AccountDetailsDto}
     */
    List<AccountDetailsDto> readAllById(List<Long> ids);

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link AccountDetailsDto}
     */
    AccountDetailsDto create(AccountDetailsDto accountDetails);

    /**
     * @param id техничский идентификатор {@link AccountDetailsEntity}.
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link AccountDetailsDto}
     */
    AccountDetailsDto update(Long id, AccountDetailsDto accountDetails);
}
