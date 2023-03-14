package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;

import java.util.List;

/**
 * Service для {@link AtmDto}
 */
public interface AtmService {

    /**
     * @param id технический идентификатор {@link AtmEntity}.
     * @return {@link AtmDto}
     */
    AtmDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link AtmEntity}
     * @return список {@link AtmDto}
     */
    List<AtmDto> readAll(List<Long> ids);

    /**
     * @param atm {@link AtmDto}
     * @return {@link AtmDto}
     */
    AtmDto save(AtmDto atm);

    /**
     * @param id  технический идентификатор {@link AtmEntity}
     * @param atm {@link AtmDto}
     * @return {@link AtmDto}
     */
    AtmDto update(Long id, AtmDto atm);
}
