package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import java.util.List;

/**
 * Service для {@link ActualRegistrationDto}.
 */
public interface ActualRegistrationService {

    /**
     * @param id техничский идентификатор {@link ActualRegistrationEntity}.
     * @return {@link ActualRegistrationDto}
     */
    ActualRegistrationDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link ActualRegistrationEntity}.
     * @return список {@link ActualRegistrationDto}
     */
    List<ActualRegistrationDto> readAll(List<Long> ids);

    /**
     * @param actualRegistration {@link ActualRegistrationDto}
     * @return {@link ActualRegistrationDto}
     */
    ActualRegistrationDto save(ActualRegistrationDto actualRegistration);

    /**
     * @param id технический идентификатор для {@link ActualRegistrationEntity}.
     * @param actualRegistration {@link ActualRegistrationDto}
     * @return {@link ActualRegistrationDto}
     */
    ActualRegistrationDto update(Long id, ActualRegistrationDto actualRegistration);
}

