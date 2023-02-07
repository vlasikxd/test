package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.mapper.SuspiciousPhoneTransferMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import com.bank.antifraud.util.ListSizeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Реализация {@link SuspiciousPhoneTransferService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SuspiciousPhoneTransferServiceImpl implements SuspiciousPhoneTransferService {

    private final static String ENTITY_NAME = "SuspiciousPhoneTransfer";

    private final SuspiciousPhoneTransferRepository repository;
    private final SuspiciousPhoneTransferMapper mapper;
    private final ListSizeValidator listSizeValidator;


    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    @Transactional
    public SuspiciousPhoneTransferDto create(SuspiciousPhoneTransferDto transfer) {
        final SuspiciousPhoneTransferEntity suspiciousPhoneTransfer = repository.save(
                mapper.toEntity(transfer)
        );
        return mapper.toDto(suspiciousPhoneTransfer);
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    public SuspiciousPhoneTransferDto read(Long id) {
        final SuspiciousPhoneTransferEntity suspiciousPhoneTransfer = findById(id);
        return mapper.toDto(suspiciousPhoneTransfer);
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    public List<SuspiciousPhoneTransferDto> readAll(List<Long> ids) {
        final List<SuspiciousPhoneTransferEntity> suspiciousPhoneTransfers = repository.findAllById(ids);
        listSizeValidator.throwIfDifferent(ids, suspiciousPhoneTransfers,
                () -> new EntityNotFoundException("Количество найденных и запрошенных записей не совпадает.")
        );
        return mapper.toListDto(suspiciousPhoneTransfers);
    }

    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    @Transactional
    public SuspiciousPhoneTransferDto update(SuspiciousPhoneTransferDto transfer, Long id) {
        final SuspiciousPhoneTransferEntity suspiciousPhoneTransferById = findById(id);
        final SuspiciousPhoneTransferEntity savedSuspiciousPhoneTransfer = repository.save(
                mapper.mergeToEntity(transfer, suspiciousPhoneTransferById)
        );
        return mapper.toDto(savedSuspiciousPhoneTransfer);
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferEntity}
     */
    SuspiciousPhoneTransferEntity findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ENTITY_NAME + " с id = " + id + " не найден.")
        );
    }
}
