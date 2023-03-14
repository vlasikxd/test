package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.LicenseService;
import com.bank.publicinfo.validator.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация для {@link LicenseService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LicenseServiceImpl implements LicenseService {

    LicenseRepository repository;
    LicenseMapper mapper;
    Validator validator;

    /**
     * @param id технический идентификатор для {@link LicenseEntity}
     * @return {@link LicenseDto}
     */
    @Override
    public LicenseDto read(Long id) {

        final LicenseEntity license = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("license с данным идентификатором не найден!")
                );

        return mapper.toDto(license);
    }

    /**
     * @param licenseDto {@link LicenseDto}
     * @return {@link LicenseDto}
     */
    @Override
    @Transactional
    public LicenseDto save(LicenseDto licenseDto) {

        final LicenseEntity license = repository.save(
                mapper.toEntity(licenseDto)
        );

        return mapper.toDto(license);
    }

    /**
     * @param licenseDto {@link LicenseDto}
     * @return {@link LicenseDto}
     */
    @Override
    @Transactional
    public LicenseDto update(Long id, LicenseDto licenseDto) {

        final LicenseEntity licenseEntityById = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Обновление невозможно, license не найден!")
                );

        final LicenseEntity license = repository.save(
                mapper.mergeToEntity(licenseDto, licenseEntityById)
        );

        return mapper.toDto(license);
    }

    /**
     * @param ids список технических идентификаторов {@link LicenseEntity}.
     * @return {@link List<LicenseDto>}.
     */
    @Override
    public List<LicenseDto> readAll(List<Long> ids) {

        final List<LicenseEntity> licenses = repository.findAllById(ids);

        validator.checkSize(
                licenses, ids,
                () -> new EntityNotFoundException("Ошибка в переданных параметрах, license не существуют(ет)")
        );

        return mapper.toDtoList(licenses);
    }
}
