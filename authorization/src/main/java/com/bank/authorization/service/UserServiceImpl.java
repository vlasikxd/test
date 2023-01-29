package com.bank.authorization.service;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


/**
 * Реализация для {@link UserService}.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * @param id технический идентификатор для {@link UserEntity}.
     * @return {@link UserDto}.
     */
    @Override
    public UserDto read(Long id) {
        final UserEntity user = repository.findById(id)
                .orElseThrow(() -> entityNotFoundException("Пользователь с данным идентификатором не существует!"));
        return mapper.toDto(user);
    }

    /**
     * @param user {@link UserDto}.
     * @return {@link UserDto}.
     */
    @Override
    @Transactional
    public UserDto save(UserDto user) {
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
                // TODO сообщение везде поправь на "пользователь не найден", так как пользователь не должен знать,
                //    что пользователь не существует.
                 .orElseThrow(() -> entityNotFoundException("Обновление невозможно, пользователя не существует!"));
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
            throw entityNotFoundException("Ошибка в переданных параметрах, пользователи(ь) не существуют(ет)");
        }

        return mapper.toDtoList(users);
    }

    // TODO entityNotFoundException переименуй в returnEntityNotFoundException.
    private EntityNotFoundException entityNotFoundException(String message) {
        return new EntityNotFoundException(message);
    }
}
