package com.bank.profile.service;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;

import java.util.List;

/**
 * Service для {@link ProfileDto}.
 */
public interface ProfileService {

    /**
     * @param id техничский идентификатор {@link ProfileEntity}.
     * @return {@link ProfileDto}
     */
    ProfileDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link ProfileEntity}.
     * @return список {@link ProfileDto}
     */
    List<ProfileDto> readAll(List<Long> ids);

    /**
     * @param profile {@link ProfileDto}
     * @return {@link ProfileDto}
     */
    ProfileDto save(ProfileDto profile);

    /**
     * @param id технический идентификатор для {@link ProfileEntity}.
     * @param profile {@link ProfileDto}
     * @return {@link ProfileDto}
     */
    ProfileDto update(Long id, ProfileDto profile);
}

