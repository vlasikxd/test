package com.bank.transfer.service.impl;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.returner.EntityNotFoundReturner;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.repository.PhoneTransferRepository;
import com.bank.transfer.service.PhoneTransferService;
import com.bank.transfer.validator.ReadAllValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация {@link PhoneTransferService}
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneTransferServiceImpl implements PhoneTransferService {

    private final PhoneTransferRepository repository;
    private final PhoneTransferMapper mapper;
    private final EntityNotFoundReturner notFoundReturner;
    private final ReadAllValidator validator;

    /**
     * @param transfer {@link PhoneTransferDto}
     * @return  {@link PhoneTransferDto}
     */
    @Override
    @Transactional
    public PhoneTransferDto create(PhoneTransferDto transfer) {
        final PhoneTransferEntity phoneTransfer = repository.save(mapper.toEntity(transfer));
        return mapper.toDto(phoneTransfer);
    }

    /**
     * @param transferDto {@link PhoneTransferDto}
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return  {@link PhoneTransferDto}
     */
    @Override
    @Transactional
    public PhoneTransferDto update(PhoneTransferDto transferDto, Long id) {
        final PhoneTransferEntity phoneTransfer = repository.findById(id).
                orElseThrow(() -> {
                    throw notFoundReturner.getException("PhoneTransfer для обновления с указанным id не найден");
                });
        final PhoneTransferEntity transfer = repository.save(mapper.mergeToEntity(transferDto, phoneTransfer));
        return mapper.toDto(transfer);
    }

    /**
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return  {@link PhoneTransferDto}
     */
    @Override
    public PhoneTransferDto read(Long id) {
        final PhoneTransferEntity phoneTransfer = repository.findById(id).
                orElseThrow(() -> {
                    throw notFoundReturner.getException("PhoneTransfer с указанным id не найден");
                });
        return mapper.toDto(phoneTransfer);
    }

    /**
     * @param ids лист технических идентификаторов {@link PhoneTransferEntity}
     * @return  {@link List<PhoneTransferDto>}
     */
    @Override
    public List<PhoneTransferDto> readAll(List<Long> ids) {
        final List<PhoneTransferDto> transfers = mapper.toDtoList(repository.findAllById(ids));
        final String exceptionMessage = "Лист содержит один и более id, по которым нет PhoneTransfer";
        validator.validation(transfers, ids, exceptionMessage);
        return transfers;
    }
}
