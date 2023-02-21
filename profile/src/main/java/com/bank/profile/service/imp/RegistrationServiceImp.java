package com.bank.profile.service.imp;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.RegistrationService;
import com.bank.profile.validator.EntityListValidator;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация для {@link RegistrationService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationServiceImp implements RegistrationService {

    RegistrationRepository repository;
    RegistrationMapper mapper;
    EntityListValidator validator;

    /**
     * @param id технический идентификатор для {@link RegistrationEntity}.
     * @return {@link RegistrationDto}.
     */
    @Override
    public RegistrationDto read(Long id) {
        final RegistrationEntity registration = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("registration с данным идентификатором не найден!")
        );
        return mapper.toDto(registration);
    }

    /**
     * @param registrationDto {@link RegistrationDto}.
     * @return {@link RegistrationDto}.
     */
    @Override
    @Transactional
    public RegistrationDto save(RegistrationDto registrationDto) {
        final RegistrationEntity registration = repository.save(mapper.toEntity(registrationDto));
        return mapper.toDto(registration);
    }

    /**
     * @param registrationDto {@link RegistrationDto}.
     * @return {@link RegistrationDto}.
     */
    @Override
    @Transactional
    public RegistrationDto update(Long id, RegistrationDto registrationDto) {
        final RegistrationEntity registrationEntityById = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Обновление невозможно, registration не найден!")
        );
        final RegistrationEntity registration = repository.save(
                mapper.mergeToEntity(registrationDto, registrationEntityById)
        );
        return mapper.toDto(registration);
    }

    /**
     * @param ids список технических идентифиакторов {@link RegistrationEntity}.
     * @return {@link List<RegistrationDto>}.
     */
    @Override
    public List<RegistrationDto> readAll(List<Long> ids) {
        final List<RegistrationEntity> registrationEntities = repository.findAllById(ids);

        validator.checkSize(
                registrationEntities,
                ids,
                () -> new EntityNotFoundException("Ошибка в переданных параметрах, registration не существуют(ет)")
        );

        return mapper.toDtoList(registrationEntities);
    }
}
