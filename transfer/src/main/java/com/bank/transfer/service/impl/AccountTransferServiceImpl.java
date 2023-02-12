package com.bank.transfer.service.impl;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.returner.EntityNotFoundReturner;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.service.AccountTransferService;
import com.bank.transfer.validator.ReadAllValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация {@link AccountTransferService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountTransferServiceImpl implements AccountTransferService {

    private final AccountTransferRepository repository;
    private final AccountTransferMapper mapper;
    private final EntityNotFoundReturner notFoundReturner;
    private final ReadAllValidator validator;

    /**
     * @param transfer {@link AccountTransferDto}
     * @return  {@link AccountTransferDto}
     */
    @Override
    @Transactional
    public AccountTransferDto create(AccountTransferDto transfer) {
        final AccountTransferEntity accountTransfer = repository.save(mapper.toEntity(transfer));
        return mapper.toDto(accountTransfer);
    }

    /**
     * @param transferDto {@link AccountTransferDto}
     * @param id технический идентификатор {@link AccountTransferEntity}
     * @return  {@link AccountTransferDto}
     */
    @Override
    @Transactional
    public AccountTransferDto update(AccountTransferDto transferDto, Long id) {
        final AccountTransferEntity accountTransfer = repository.findById(id).
                orElseThrow(() -> {
                    throw notFoundReturner.getException("AccountTransfer для обновления с указанным id не найден");
                });
        final AccountTransferEntity transfer = repository.save(mapper.mergeToEntity(transferDto, accountTransfer));
        return mapper.toDto(transfer);
    }

    /**
     * @param id технический идентификатор {@link AccountTransferEntity}
     * @return  {@link AccountTransferDto}
     */
    @Override
    public AccountTransferDto read(Long id) {
        final AccountTransferEntity transfer = repository.findById(id).
                orElseThrow(() -> {
                    throw notFoundReturner.getException("AccountTransfer с указанным id не найден");
                });
        return mapper.toDto(transfer);
    }

    /**
     * @param ids лист технических идентификаторов {@link AccountTransferEntity}
     * @return  {@link List<AccountTransferDto>}
     */
    @Override
    public List<AccountTransferDto> readAll(List<Long> ids) {
        final String exceptionMessage = "Лист содержит один и более id, по которым нет AccountTransfer";
        final List<AccountTransferDto> transfers = mapper.toDtoList(repository.findAllById(ids));
        validator.validation(transfers, ids, exceptionMessage);
        return transfers;
    }
}
