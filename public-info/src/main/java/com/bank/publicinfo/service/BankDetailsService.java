package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;

import java.util.List;

/**
 * Service для {@link BankDetailsDto}
 */
public interface BankDetailsService {

    /**
     * @param id технический идентификатор {@link BankDetailsEntity}.
     * @return {@link BankDetailsDto}
     */
    BankDetailsDto read(Long id);

    /**
     * @param ids список технических индентификаторов {@link BankDetailsEntity}
     * @return список {@link BankDetailsDto}
     */
    List<BankDetailsDto> readAll(List<Long> ids);

    /**
     * @param bankDetails {@link BankDetailsDto}
     * @return {@link BankDetailsDto}
     */
    BankDetailsDto save(BankDetailsDto bankDetails);

    /**
     * @param id          технический идентификатор {@link BankDetailsEntity}
     * @param bankDetails {@link BankDetailsDto}
     * @return {@link BankDetailsDto}
     */
    BankDetailsDto update(Long id, BankDetailsDto bankDetails);
}
