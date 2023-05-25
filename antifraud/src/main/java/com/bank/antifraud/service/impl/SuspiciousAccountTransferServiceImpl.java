package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.transferDto.AccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.feign.TransferAccountClient;
import com.bank.antifraud.mapper.SuspiciousAccountTransferMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.antifraud.validator.AccountValidator;
import com.bank.antifraud.validator.ValidatorSize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Реализация {@link SuspiciousAccountTransferService}
 */
@Service
@RequiredArgsConstructor
public class SuspiciousAccountTransferServiceImpl implements SuspiciousAccountTransferService {

    private final SuspiciousAccountTransferRepository repository;
    private final SuspiciousAccountTransferMapper mapper;
    private final ValidatorSize validatorSize;
    private final TransferAccountClient transferClient;

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @return {@link SuspiciousAccountTransferDto}
     */
    @Override
    @Transactional
    public SuspiciousAccountTransferDto create(SuspiciousAccountTransferDto transfer) {
        if (transfer != null) {
            AccountValidator.dtoValidator(transfer.getIsBlocked(), transfer.getBlockedReason());
        }
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
        SuspiciousAccountTransferDto suspiciousAccountTransferDto = mapper.toDto(findById(id));
        suspiciousAccountTransferDto.setAccountTransferId(transferClient.read(suspiciousAccountTransferDto.getAccountTransferId()).getBody());
        return suspiciousAccountTransferDto;
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    @Override
    public List<SuspiciousAccountTransferDto> readAll(List<Long> ids) {
        final List<SuspiciousAccountTransferEntity> suspiciousAccountTransfers = repository.findAllById(ids);
        validatorSize.checkSize(ids, suspiciousAccountTransfers,
                () -> new EntityNotFoundException("Количество найденных и запрошенных записей не совпадает."));
        List<SuspiciousAccountTransferDto> suspiciousAccountTransferDto = mapper.toListDto(suspiciousAccountTransfers);
        suspiciousAccountTransferDto.forEach(x -> x.setAccountTransferId(transferClient.read(x.getAccountTransferId()).getBody()));
        return suspiciousAccountTransferDto;
    }

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @param id       технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    @Override
    @Transactional
    public SuspiciousAccountTransferDto update(SuspiciousAccountTransferDto transfer, Long id) {
        if (transfer != null) {
            AccountValidator.dtoValidator(transfer.getIsBlocked(), transfer.getBlockedReason());
        }
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
    private SuspiciousAccountTransferEntity findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("SuspiciousAccountTransfer с id = " + id + " не найден.")
        );
    }

//    /**
//     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
//     * @return {@link ResponseEntity} c {@link AccountTransferDto} и {@link HttpStatus}
//     */
//    public ResponseEntity<AccountTransferDto> readTransfer(Long id) {
//        return transferClient.read(findById(id).getAccountTransferId());
//    }
//
//    /**
//     * @param ids список технических идентификаторов {@link SuspiciousAccountTransferEntity}
//     * @return {@link ResponseEntity} со списком {@link AccountTransferDto} и {@link HttpStatus}
//     */
//    public ResponseEntity<List<AccountTransferDto>> readAllTransfer(List<Long> ids) {
//        final List<Long> accountTransferIds = readAll(ids).stream()
//                .map(SuspiciousAccountTransferDto::getAccountTransferId).toList();
//        return transferClient.readAll(accountTransferIds);
//    }
}
