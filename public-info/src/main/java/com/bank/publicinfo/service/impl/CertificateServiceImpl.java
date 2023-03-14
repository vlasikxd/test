package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.CertificateService;
import com.bank.publicinfo.validator.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация для {@link CertificateService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CertificateServiceImpl implements CertificateService {

    CertificateRepository repository;
    CertificateMapper mapper;
    Validator validator;

    /**
     * @param id технический идентификатор для {@link CertificateEntity}
     * @return {@link CertificateDto}
     */
    @Override
    public CertificateDto read(Long id) {

        final CertificateEntity certificate = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("certificate с данным идентификатором не найден!")
                );

        return mapper.toDto(certificate);
    }

    /**
     * @param certificateDto {@link CertificateDto}
     * @return {@link CertificateDto}
     */
    @Override
    @Transactional
    public CertificateDto save(CertificateDto certificateDto) {

        final CertificateEntity certificate = repository.save(
                mapper.toEntity(certificateDto)
        );

        return mapper.toDto(certificate);
    }

    /**
     * @param certificateDto {@link CertificateDto}
     * @return {@link CertificateDto}
     */
    @Override
    @Transactional
    public CertificateDto update(Long id, CertificateDto certificateDto) {

        final CertificateEntity certificateById = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Обновление невозможно, certificate не найден!")
                );

        final CertificateEntity certificate = repository.save(
                mapper.mergeToEntity(certificateDto, certificateById)
        );

        return mapper.toDto(certificate);
    }

    /**
     * @param ids список технических идентификаторов {@link CertificateEntity}.
     * @return {@link List<CertificateDto>}.
     */
    @Override
    public List<CertificateDto> readAll(List<Long> ids) {

        final List<CertificateEntity> certificates = repository.findAllById(ids);

        validator.checkSize(
                certificates, ids,
                () -> new EntityNotFoundException("Ошибка в переданных параметрах, certificate не существуют(ет)")
        );

        return mapper.toDtoList(certificates);
    }
}
