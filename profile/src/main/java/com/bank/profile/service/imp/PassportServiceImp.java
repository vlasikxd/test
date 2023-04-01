package com.bank.profile.service.imp;

import com.bank.common.exception.ValidationException;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.PassportService;
import com.bank.profile.validator.EntityListValidator;
import com.bank.profile.validator.DtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация для {@link PassportService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PassportServiceImp implements PassportService {

    PassportRepository repository;
    PassportMapper mapper;
    EntityListValidator validator;
    DtoValidator<PassportDto> dtoValidator;

    /**
     * @param id технический идентификатор для {@link PassportEntity}.
     * @return {@link PassportDto}.
     */
    @Override
    public PassportDto read(Long id) {
        final PassportEntity passport = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("passport с данным id не найден!")
        );
        return mapper.toDto(passport);
    }

    /**
     * @param passportDto {@link PassportDto}.
     * @return {@link PassportDto}.
     */
    @Override
    @Transactional
    public PassportDto save(PassportDto passportDto) {
        final PassportEntity passport = repository.save(mapper.toEntity(passportDto));

        dtoValidator.validate(
                passportDto,
                () -> new ValidationException("Сохранение невозможно, неверные данные")
        );

        return mapper.toDto(passport);
    }

    /**
     * @param passportDto {@link PassportDto}.
     * @return {@link PassportDto}.
     */
    @Override
    @Transactional
    public PassportDto update(Long id, PassportDto passportDto) {
        final PassportEntity passportEntityById = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Обновление невозможно, passport не найден!")
        );
        final PassportEntity actualRegistration = repository.save(
                mapper.mergeToEntity(passportDto, passportEntityById)
        );

        dtoValidator.validate(
                passportDto,
                () -> new ValidationException("Обновление невозможно, неверные данные")
        );

        return mapper.toDto(actualRegistration);
    }

    /**
     * @param ids список технических идентификаторов {@link PassportEntity}.
     * @return {@link List<PassportDto>}.
     */
    @Override
    public List<PassportDto> readAll(List<Long> ids) {
        final List<PassportEntity> passportEntities = repository.findAllById(ids);

        validator.checkSize(
                passportEntities,
                ids,
                () -> new EntityNotFoundException("Ошибка в переданных параметрах, passport не существуют(ет)")
        );

        return mapper.toDtoList(passportEntities);
    }
}
