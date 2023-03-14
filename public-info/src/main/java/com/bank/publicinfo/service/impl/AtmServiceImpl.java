package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.service.AtmService;
import com.bank.publicinfo.validator.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация для {@link AtmService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AtmServiceImpl implements AtmService {

    AtmRepository repository;
    AtmMapper mapper;
    Validator validator;

    /**
     * @param id технический идентификатор для {@link AtmEntity}
     * @return {@link AtmDto}
     */
    @Override
    public AtmDto read(Long id) {
        final AtmEntity atm = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("atm с данным идентификатором не найден!")
                );
        return mapper.toDto(atm);
    }

    /**
     * @param atmDto {@link AtmDto}
     * @return {@link AtmDto}
     */
    @Override
    @Transactional
    public AtmDto save(AtmDto atmDto) {
        final AtmEntity atm = repository.save(
                mapper.toEntity(atmDto)
        );

        return mapper.toDto(atm);
    }

    /**
     * @param id     технический идентификатор для {@link AtmEntity}
     * @param atmDto {@link AtmDto}
     * @return {@link AtmDto}
     */
    @Override
    @Transactional
    public AtmDto update(Long id, AtmDto atmDto) {

        final AtmEntity atmById = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Обновление невозможно, atm не найден!")
                );

        final AtmEntity atm = repository.save(
                mapper.mergeToEntity(atmDto, atmById)
        );

        return mapper.toDto(atm);
    }

    /**
     * @param ids список технических идентификаторов {@link AtmEntity}.
     * @return {@link List<AtmDto>}.
     */
    @Override
    public List<AtmDto> readAll(List<Long> ids) {

        final List<AtmEntity> atms = repository.findAllById(ids);

        validator.checkSize(
                atms, ids, () -> new EntityNotFoundException("Ошибка в переданных параметрах, atm не существуют(ет)")
        );

        return mapper.toDtoList(atms);
    }
}

