package com.bank.authorization.service;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Реализация для {@link UserService}.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository repository;

    UserMapper mapper;

    PasswordEncoder passwordEncoder;


    /**
     * @param id технический идентификатор для {@link UserEntity}.
     * @return {@link UserDto}.
     */
    @Override
    public UserDto read(Long id) {
        final UserEntity user = repository.findById(id)
                .orElseThrow(() -> returnEntityNotFoundException("Пользователь с данным id не найден!"));
        return mapper.toDto(user);
    }

    /**
     * @param username имя пользователя для {@link UserEntity}.
     * @return {@link UserDto}.
     */
    public UserDto readByUserName(String username) {
        final UserEntity user = repository.findByUsername(username)
                .orElseThrow(() -> returnEntityNotFoundException("Пользователь с данным username не найден!"));
        return mapper.toDto(user);
    }

    /**
     * @param user {@link UserDto}.
     * @return {@link UserDto}.
     */
    @Override
    @Transactional
    public UserDto save(UserDto user) {
        if (user != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        final UserEntity userEntity = repository.save(mapper.toEntity(user));
        return mapper.toDto(userEntity);
    }

    /**
     * @param id   технический идентификатор для {@link UserEntity}.
     * @param userDto {@link UserDto}.
     * @return {@link UserDto}.
     */
    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        final UserEntity userById = repository.findById(id)
                 .orElseThrow(() -> returnEntityNotFoundException("Обновление невозможно, пользователь не найден!"));
        if (userDto != null && userDto.getPassword() != null) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        final UserEntity user = repository.save(mapper.mergeToEntity(userDto, userById));
        return mapper.toDto(user);
    }

    /**
     * @param ids список технических идентифиакторов {@link UserEntity}.
     * @return {@link List<UserDto>}.
     */
    @Override
    public List<UserDto> readAll(List<Long> ids) {
        final List<UserEntity> users = repository.findAllById(ids);

        if (users.size() != ids.size()) {
            throw returnEntityNotFoundException("Ошибка в переданных параметрах, пользователь(и) не найден(ы)");
        }

        return mapper.toDtoList(users);
    }

    private EntityNotFoundException returnEntityNotFoundException(String message) {
        return new EntityNotFoundException(message);
    }
}
