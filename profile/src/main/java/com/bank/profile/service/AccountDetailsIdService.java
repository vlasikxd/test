package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;

import java.util.List;

/**
 * Service для {@link AccountDetailsIdDto}.
 */
public interface AccountDetailsIdService {

    /**
     * @param id техничский идентификатор {@link AccountDetailsIdEntity}.
     * @return {@link AccountDetailsIdDto}
     */
    AccountDetailsIdDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link AccountDetailsIdEntity}.
     * @return список {@link AccountDetailsIdDto}
     */
    List<AccountDetailsIdDto> readAll(List<Long> ids);

    /**
     * @param accountDetailsIdDto {@link AccountDetailsIdDto}
     * @return {@link AccountDetailsIdDto}
     */
    AccountDetailsIdDto save(AccountDetailsIdDto accountDetailsIdDto);

    /**
     * @param id технический идентификатор для {@link AccountDetailsIdEntity}.
     * @param accountDetailsId {@link AccountDetailsIdDto}.
     * @return {@link AccountDetailsIdDto}.
     */
    AccountDetailsIdDto update(Long id, AccountDetailsIdDto accountDetailsId);
}
