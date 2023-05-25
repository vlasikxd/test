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
        return mapper.toDto(suspiciousPhoneTransfer);
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    @Override
    public SuspiciousPhoneTransferDto read(Long id) {
        SuspiciousPhoneTransferDto suspiciousPhoneTransferDto = mapper.toDto(findById(id));
        suspiciousPhoneTransferDto.setPhoneTransferId(transferClient.read(suspiciousPhoneTransferDto.getPhoneTransferId()).getBody());
        return suspiciousPhoneTransferDto;
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
        List<SuspiciousPhoneTransferDto> suspiciousPhoneTransferDto = mapper.toListDto(suspiciousPhoneTransfers);
        suspiciousPhoneTransferDto.forEach(x -> x.setPhoneTransferId(transferClient.read(x.getPhoneTransferId()).getBody()));
        return suspiciousPhoneTransferDto;
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
        return mapper.toDto(savedSuspiciousPhoneTransfer);
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
}
