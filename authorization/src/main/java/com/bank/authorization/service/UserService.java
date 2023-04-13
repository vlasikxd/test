package com.bank.authorization.service;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;

import java.util.List;

/**
 * Service для {@link UserDto}.
 */
public interface UserService {

    /**
     * @param id технический идентификатор для {@link UserEntity}.
     * @return {@link UserDto}.
     */
    UserDto read(Long id);

    /**
     * @param username имя пользователя для {@link UserEntity}.
     * @return {@link UserDto}.
     */
    UserDto readByUserName(String username);

    /**
     * @param user {@link UserDto}.
     * @return {@link UserDto}.
     */
    UserDto save(UserDto user);

    /**
     * @param id технический идентификатор для {@link UserEntity}.
     * @param user {@link UserDto}.
     * @return {@link UserDto}.
     */
    UserDto update(Long id, UserDto user);

    /**
     * @param ids список технических идентификаторов {@link UserEntity}.
     * @return {@link List<UserDto>}.
     */
    List<UserDto> readAll(List<Long> ids);
}
