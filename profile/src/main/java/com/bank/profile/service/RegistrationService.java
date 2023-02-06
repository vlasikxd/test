package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;

import java.util.List;

/**
 * Service для {@link RegistrationDto}.
 */
public interface RegistrationService {

    /**
     * @param id техничский идентификатор {@link RegistrationEntity}.
     * @return {@link RegistrationDto}
     */
    RegistrationDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link RegistrationEntity}.
     * @return список {@link RegistrationDto}
     */
    List<RegistrationDto> readAll(List<Long> ids);

    /**
     * @param registration {@link RegistrationDto}
     * @return {@link RegistrationDto}
     */
    RegistrationDto save(RegistrationDto registration);

    /**
     * @param id технический идентификатор для {@link RegistrationEntity}.
     * @param registration {@link RegistrationDto}
     * @return {@link RegistrationDto}
     */
    RegistrationDto update(Long id, RegistrationDto registration);
}
