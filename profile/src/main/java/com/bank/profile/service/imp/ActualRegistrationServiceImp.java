package com.bank.profile.service.imp;

import com.bank.common.exception.ValidationException;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.ActualRegistrationService;
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
 * Реализация для {@link ActualRegistrationService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActualRegistrationServiceImp implements ActualRegistrationService {

    ActualRegistrationRepository repository;
    ActualRegistrationMapper mapper;
    EntityListValidator validator;
    DtoValidator<ActualRegistrationDto> dtoValidator;

    /**
     * @param id технический идентификатор для {@link ActualRegistrationEntity}.
     * @return {@link ActualRegistrationDto}.
     */
    @Override
    public ActualRegistrationDto read(Long id) {
        final ActualRegistrationEntity actualRegistration = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("actualRegistration с данным id не найден!")
        );
        return mapper.toDto(actualRegistration);
    }

    /**
     * @param actualRegistrationDto {@link ActualRegistrationDto}.
     * @return {@link ActualRegistrationDto}.
     */
    @Override
    @Transactional
    public ActualRegistrationDto save(ActualRegistrationDto actualRegistrationDto) {
        final ActualRegistrationEntity actualRegistration = repository.save(mapper.toEntity(actualRegistrationDto));

        dtoValidator.validate(
                actualRegistrationDto,
                () -> new ValidationException("Сохранение невозможно, неверные данные")
        );

        return mapper.toDto(actualRegistration);
    }

    /**
     * @param id   технический идентификатор для {@link AccountDetailsIdEntity}.
     * @param actualRegistrationDto {@link ActualRegistrationDto}.
     * @return {@link ActualRegistrationDto}.
     */
    @Override
    @Transactional
    public ActualRegistrationDto update(Long id, ActualRegistrationDto actualRegistrationDto) {
        final ActualRegistrationEntity registrationById = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Обновление невозможно, ActualRegistration не найден!")
        );
        final ActualRegistrationEntity registration = repository.save(
                mapper.mergeToEntity(actualRegistrationDto, registrationById)
        );
        dtoValidator.validate(
                actualRegistrationDto,
                () -> new ValidationException("Обновление невозможно, неверные данные")
        );

        return mapper.toDto(registration);
    }

    /**
     * @param ids список технических идентификаторов {@link ActualRegistrationEntity}.
     * @return {@link List<ActualRegistrationDto>}.
     */
    @Override
    public List<ActualRegistrationDto> readAll(List<Long> ids) {
        final List<ActualRegistrationEntity> actualRegistrationEntities = repository.findAllById(ids);

        validator.checkSize(
                actualRegistrationEntities,
                ids,
                () -> new EntityNotFoundException(
                        "Ошибка в переданных параметрах, ActualRegistration не существуют(ет)"
                )
        );

        return mapper.toDtoList(actualRegistrationEntities);
    }
}

