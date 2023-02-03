package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.repository.AccountDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Реализация {@link AccountDetailsService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDetailsServiceImp implements AccountDetailsService {

    private final AccountDetailsRepository repository;

    private final AccountDetailsMapper mapper;

    /**
     * @param id техничский идентификатор {@link AccountDetailsEntity}.
     * @return {@link AccountDetailsDto}
     */
    @Override
    public AccountDetailsDto readById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> returnEntityNotFoundException("AccountDetails с таким id нет в базе данных"))
        );
    }

    /**
     * @param ids список технических идентификаторов {@link AccountDetailsEntity}.
     * @return список {@link AccountDetailsDto}
     */
    @Override
    public List<AccountDetailsDto> readAllById(List<Long> ids) {
        final List<AccountDetailsEntity> accountDetailsList = repository.findAllById(ids);
        if (ids.size() > accountDetailsList.size()) {
            throw returnEntityNotFoundException("Одного или нескольких id из списка нет в базе данных");
        }
        return mapper.toDtoList(accountDetailsList);
    }

    /**
     * @param accountDetailsDto {@link AccountDetailsDto}
     * @return {@link AccountDetailsDto}
     */
    @Override
    @Transactional
    public AccountDetailsDto create(AccountDetailsDto accountDetailsDto) {
        final AccountDetailsEntity accountDetails = repository.save(mapper.toEntity(accountDetailsDto));
        return mapper.toDto(accountDetails);
    }

    /**
     * @param id техничский идентификатор {@link AccountDetailsEntity}.
     * @param accountDetailsDto {@link AccountDetailsDto}
     * @return {@link AccountDetailsDto}
     */
    @Override
    @Transactional
    public AccountDetailsDto update(Long id, AccountDetailsDto accountDetailsDto) {
        final AccountDetailsEntity accountDetailsEntity = repository.findById(id)
                .orElseThrow(() -> returnEntityNotFoundException("Не существующий id = " + id)
        );
        final AccountDetailsEntity accountDetails = repository.save(mapper.mergeToEntity(accountDetailsDto,
                accountDetailsEntity));
        return mapper.toDto(accountDetails);
    }

    private EntityNotFoundException returnEntityNotFoundException(String massage) {
        final EntityNotFoundException exception = new EntityNotFoundException(massage);
        log.error(exception.getMessage());
        return exception;
    }
}
