package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.dto.transferDto.PhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.feign.TransferPhoneClient;
import com.bank.antifraud.mapper.SuspiciousPhoneTransferMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import com.bank.antifraud.validator.PhoneValidator;
import com.bank.antifraud.validator.ValidatorSize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        validatorSize.checkSize(ids, suspiciousPhoneTransfers,
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

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} c {@link PhoneTransferDto} и {@link HttpStatus}
     */
    public ResponseEntity<PhoneTransferDto> readTransfer(Long id) {
        return transferClient.read(findById(id).getPhoneTransferId());
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} со списком {@link PhoneTransferDto} и {@link HttpStatus}
     */
    public ResponseEntity<List<PhoneTransferDto>> readAllTransfer(List<Long> ids) {
        final List<Long> phoneTransferIds = readAll(ids).stream()
                .map(SuspiciousPhoneTransferDto::getPhoneTransferId).toList();
        return transferClient.readAll(phoneTransferIds);
    }
}
