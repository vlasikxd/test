package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.BranchService;
import com.bank.publicinfo.validator.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация для {@link BranchService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BranchServiceImpl implements BranchService {

    BranchRepository repository;
    BranchMapper mapper;
    Validator validator;

    /**
     * @param id технический идентификатор для {@link BranchEntity}
     * @return {@link BranchDto}
     */
    @Override
    public BranchDto read(Long id) {

        final BranchEntity branch = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("branch с данным идентификатором не найден!")
                );

        return mapper.toDto(branch);
    }

    /**
     * @param branchDto {@link BranchDto}
     * @return {@link BranchDto}
     */
    @Override
    @Transactional
    public BranchDto save(BranchDto branchDto) {

        final BranchEntity branch = repository.save(
                mapper.toEntity(branchDto)
        );

        return mapper.toDto(branch);
    }

    /**
     * @param branchDto {@link BranchDto}.
     * @return {@link BranchDto}.
     */
    @Override
    @Transactional
    public BranchDto update(Long id, BranchDto branchDto) {

        final BranchEntity branchById = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Обновление невозможно, branch не найден!")
                );

        final BranchEntity atm = repository.save(
                mapper.mergeToEntity(branchDto, branchById)
        );

        return mapper.toDto(atm);
    }

    /**
     * @param ids список технических идентификаторов {@link BranchDto}.
     * @return {@link List<BranchDto>}.
     */
    @Override
    public List<BranchDto> readAll(List<Long> ids) {

        final List<BranchEntity> branches = repository.findAllById(ids);

        validator.checkSize(
                branches, ids,
                () -> new EntityNotFoundException("Ошибка в переданных параметрах, branch не существуют(ет)")
        );

        return mapper.toDtoList(branches);
    }
}
