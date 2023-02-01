package com.bank.transfer.service.impl;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.exception.EntityNotFoundReturner;
import com.bank.transfer.mapper.AccountMapper;
import com.bank.transfer.repository.AccountRepository;
import com.bank.transfer.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO Реализация {@link AccountService}.
 * Реализация сервис слоя для {@link AccountTransferDto}, {@link AccountTransferEntity}
 */
@Slf4j
// TODO @RequiredArgsConstructor и @Service поменяй местами.
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final EntityNotFoundReturner notFoundReturner;

    /**
     * @param transfer {@link AccountTransferDto}
     * @return  {@link AccountTransferDto}
     */
    @Override
    @Transactional
    public AccountTransferDto create(AccountTransferDto transfer) {
        // TODO entity переименуй в accountTransfer. И почему локальная переменная не final, это не пройдет checkstyle.
        AccountTransferEntity entity = repository.save(mapper.toEntity(transfer));
        return mapper.toDto(entity);
    }

    /**
     * @param transfer {@link AccountTransferDto}
     * @param id технический идентификатор {@link AccountTransferEntity}
     * @return  {@link AccountTransferDto}
     */
    @Override
    @Transactional
    // TODO transfer переименуй в transferDto.
    public AccountTransferDto update(AccountTransferDto transfer, Long id) {
        final AccountTransferEntity accountTransfer = repository.findById(id).
                orElseThrow(() -> {
                    // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
                    throw notFoundReturner.getException("Запись с указанным id не найдена");
                });
        // TODO transferEntity переименуй в transfer. и смысл save(mapper.mergeToEntity(transfer, accountTransfer)); в этом переносе?
        final AccountTransferEntity transferEntity = repository.
                save(mapper.mergeToEntity(transfer, accountTransfer));
        return mapper.toDto(transferEntity);
    }

    /**
     * @param id технический идентификатор {@link AccountTransferEntity}
     * @return  {@link AccountTransferDto}
     */
    @Override
    public AccountTransferDto read(Long id) {
        final AccountTransferEntity transfer = repository.findById(id).
                orElseThrow(() -> {
                    // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
                    throw notFoundReturner.getException("Запись с указанным id не найдена");
                });
        return mapper.toDto(transfer);
    }

    /**
     * @param ids лист технических идентификаторов {@link AccountTransferEntity}
     * @return  {@link List<AccountTransferDto>}
     */
    @Override
    public List<AccountTransferDto> readAll(List<Long> ids) {
        final List<AccountTransferDto> transfers = mapper.toDtoList(repository.findAllById(ids));
        // TODO вот эта проверка(if) дублируется во всех реализациях, сделай отдельный класс и переиспользуй, как с
        //  notFoundReturner.getException
        if (ids.size() != transfers.size()) {
            // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
            throw notFoundReturner.getException("Одна или более записей из списка id не найдены");
        }
        return transfers;
    }
}
