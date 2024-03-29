package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.feign.TransferPhoneClient;
import com.bank.antifraud.mapper.SuspiciousPhoneTransferMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import com.bank.antifraud.validator.PhoneValidator;
import com.bank.antifraud.validator.ValidatorSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


/**
 * Реализация {@link SuspiciousPhoneTransferService}
 */
@Service
@RequiredArgsConstructor
public class SuspiciousPhoneTransferServiceImpl implements SuspiciousPhoneTransferService {

    private final SuspiciousPhoneTransferRepository repository;
    private final SuspiciousPhoneTransferMapper mapper;
    private final ValidatorSize validatorSize;
    private final TransferPhoneClient transferClient;
    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    @Transactional
    public SuspiciousPhoneTransferDto create(SuspiciousPhoneTransferDto transfer) {
        if (transfer != null) {
            PhoneValidator.dtoValidator(transfer.getIsBlocked(), transfer.getBlockedReason());
        }
        final SuspiciousPhoneTransferEntity suspiciousPhoneTransfer = repository.save(
                mapper.toEntity(transfer)
        );
        return setPhoneTransferDto(mapper.toDto(suspiciousPhoneTransfer));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    public SuspiciousPhoneTransferDto read(Long id) {
        return setPhoneTransferDto(mapper.toDto(findById(id)));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    public List<SuspiciousPhoneTransferDto> readAll(List<Long> ids) {
        final List<SuspiciousPhoneTransferEntity> suspiciousPhoneTransfers = repository.findAllById(ids);
        validatorSize.checkSize(ids, suspiciousPhoneTransfers,
                () -> new EntityNotFoundException("Количество найденных и запрошенных записей не совпадает."));
        final var suspiciousPhoneTransferDtoList = mapper.toListDto(suspiciousPhoneTransfers);
        suspiciousPhoneTransferDtoList.forEach(x -> x.setPhoneTransferId(
                transferClient.read(x.getPhoneTransferId().getId()).getBody()));
        return suspiciousPhoneTransferDtoList;
    }

    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    @Transactional
    public SuspiciousPhoneTransferDto update(SuspiciousPhoneTransferDto transfer, Long id) {
        if (transfer != null) {
            PhoneValidator.dtoValidator(transfer.getIsBlocked(), transfer.getBlockedReason());
        }
        final SuspiciousPhoneTransferEntity suspiciousPhoneTransferById = findById(id);
        final SuspiciousPhoneTransferEntity savedSuspiciousPhoneTransfer = repository.save(
                mapper.mergeToEntity(transfer, suspiciousPhoneTransferById)
        );
        return setPhoneTransferDto(mapper.toDto(savedSuspiciousPhoneTransfer));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferEntity}
     */
    private SuspiciousPhoneTransferEntity findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("SuspiciousPhoneTransfer с id = " + id + " не найден.")
        );
    }

    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    private SuspiciousPhoneTransferDto setPhoneTransferDto(SuspiciousPhoneTransferDto transfer) {
        transfer.setPhoneTransferId(
                transferClient.read(transfer.getPhoneTransferId().getId()).getBody());
        return transfer;
    }
}
