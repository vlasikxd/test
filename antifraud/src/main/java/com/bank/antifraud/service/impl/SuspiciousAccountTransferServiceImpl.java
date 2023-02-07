package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.mapper.SuspiciousAccountTransferMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.antifraud.util.ListSizeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Реализация {@link SuspiciousAccountTransferService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SuspiciousAccountTransferServiceImpl implements SuspiciousAccountTransferService {

    private final static String ENTITY_NAME = "SuspiciousAccountTransfer";

    private final SuspiciousAccountTransferRepository repository;
    private final SuspiciousAccountTransferMapper mapper;
    private final ListSizeValidator listSizeValidator;

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @return {@link SuspiciousAccountTransferDto}
     */
    @Override
    @Transactional
    public SuspiciousAccountTransferDto create(SuspiciousAccountTransferDto transfer) {
        final SuspiciousAccountTransferEntity suspiciousAccountTransfer = repository.save(
                mapper.toEntity(transfer)
        );
        return mapper.toDto(suspiciousAccountTransfer);
    }

    /**
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    @Override
    public SuspiciousAccountTransferDto read(Long id) {
        final SuspiciousAccountTransferEntity suspiciousAccountTransfer = findById(id);
        return mapper.toDto(suspiciousAccountTransfer);
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    @Override
    public List<SuspiciousAccountTransferDto> readAll(List<Long> ids) {
        final List<SuspiciousAccountTransferEntity> suspiciousAccountTransfers = repository.findAllById(ids);
        listSizeValidator.throwIfDifferent(ids, suspiciousAccountTransfers,
                () -> new EntityNotFoundException("Количество найденных и запрошенных записей не совпадает.")
        );
        return mapper.toListDto(suspiciousAccountTransfers);
    }

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    @Override
    @Transactional
    public SuspiciousAccountTransferDto update(SuspiciousAccountTransferDto transfer, Long id) {
        final SuspiciousAccountTransferEntity suspiciousAccountTransferById = findById(id);
        final SuspiciousAccountTransferEntity savedSuspiciousAccountTransfer = repository.save(
                mapper.mergeToEntity(transfer, suspiciousAccountTransferById)
        );
        return mapper.toDto(savedSuspiciousAccountTransfer);
    }

    /**
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferEntity}
     */
    SuspiciousAccountTransferEntity findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ENTITY_NAME + " с id = " + id + " не найден.")
        );
    }
}
