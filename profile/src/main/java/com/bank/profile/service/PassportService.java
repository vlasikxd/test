package com.bank.profile.service;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;

import java.util.List;

/**
 * Service для {@link PassportDto}.
 */
public interface PassportService {

    /**
     * @param id техничский идентификатор {@link PassportEntity}.
     * @return {@link PassportDto}
     */
    PassportDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link PassportEntity}.
     * @return список {@link PassportDto}
     */
    List<PassportDto> readAll(List<Long> ids);

    /**
     * @param passport {@link PassportDto}
     * @return {@link PassportDto}
     */
    PassportDto save(PassportDto passport);

    /**
     * @param id технический идентификатор для {@link PassportEntity}.
     * @param passport {@link PassportDto}
     * @return {@link PassportDto}
     */
    PassportDto update(Long id, PassportDto passport);
}

