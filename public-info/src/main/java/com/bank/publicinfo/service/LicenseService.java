package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;

import java.util.List;

/**
 * Service для {@link LicenseDto}
 */
public interface LicenseService {

    /**
     * @param id технический идентификатор {@link LicenseEntity}
     * @return {@link LicenseDto}
     */
    LicenseDto read(Long id);

    /**
     * @param ids список технических индентификаторов {@link LicenseEntity}
     * @return список {@link LicenseDto}
     */
    List<LicenseDto> readAll(List<Long> ids);

    /**
     * @param license {@link LicenseDto}
     * @return {@link LicenseDto}
     */
    LicenseDto save(LicenseDto license);

    /**
     * @param id      технический идентификатор для {@link LicenseEntity}
     * @param license {@link LicenseDto}
     * @return {@link LicenseDto}
     */
    LicenseDto update(Long id, LicenseDto license);
}
