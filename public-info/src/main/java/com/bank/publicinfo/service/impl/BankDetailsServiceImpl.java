package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.service.BankDetailsService;
import com.bank.publicinfo.validator.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация для {@link BankDetailsService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankDetailsServiceImpl implements BankDetailsService {

    BankDetailsRepository repository;
    BankDetailsMapper mapper;
    Validator validator;

    /**
     * @param id технический идентификатор для {@link BankDetailsEntity}.
     * @return {@link BankDetailsDto}.
     */
    @Override
    public BankDetailsDto read(Long id) {
        final BankDetailsEntity bankDetails = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("bankDetails с данным идентификатором не найден!")
                );
        return mapper.toDto(bankDetails);
    }

    /**
     * @param bankDetailsDto {@link BankDetailsDto}.
     * @return {@link BankDetailsDto}.
     */
    @Override
    @Transactional
    public BankDetailsDto save(BankDetailsDto bankDetailsDto) {

        final BankDetailsEntity bankDetails = repository.save(
                mapper.toEntity(bankDetailsDto)
        );

        return mapper.toDto(bankDetails);
    }

    /**
     * @param id             технический идентификатор для {@link BankDetailsEntity}.
     * @param bankDetailsDto {@link BankDetailsDto}.
     * @return {@link BankDetailsDto}.
     */
    @Override
    @Transactional
    public BankDetailsDto update(Long id, BankDetailsDto bankDetailsDto) {

        final BankDetailsEntity bankDetailsById = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Обновление невозможно, bankDetails не найден!")
                );

        final BankDetailsEntity bankDetails = repository.save(
                mapper.mergeToEntity(bankDetailsDto, bankDetailsById)
        );

        return mapper.toDto(bankDetails);
    }

    /**
     * @param ids список технических идентификаторов {@link BankDetailsEntity}.
     * @return {@link List<BankDetailsDto>}.
     */
    @Override
    public List<BankDetailsDto> readAll(List<Long> ids) {

        final List<BankDetailsEntity> bankDetailsList = repository.findAllById(ids);

        validator.checkSize(
                bankDetailsList, ids,
                () -> new EntityNotFoundException("Ошибка в переданных параметрах, bankDetails не существуют(ет)")
        );

        return mapper.toDtoList(bankDetailsList);
    }
}
