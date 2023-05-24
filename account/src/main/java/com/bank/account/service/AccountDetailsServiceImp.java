package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.feign.ProfileFeignClient;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.repository.AccountDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Реализация {@link AccountDetailsService}
 */
@Service
@RequiredArgsConstructor
public class AccountDetailsServiceImp implements AccountDetailsService {

    private final AccountDetailsRepository repository;

    private final AccountDetailsMapper mapper;

    private final ProfileFeignClient profile;

    /**
     * @param id техничский идентификатор {@link AccountDetailsEntity}.
     * @return {@link AccountDetailsDto}
     */
    @Override
    public AccountDetailsDto readById(Long id) {
        AccountDetailsDto accountDetailsDto = mapper.toDto(repository.findById(id)
                .orElseThrow(() -> returnEntityNotFoundException("AccountDetails с таким id не найдено")));
        accountDetailsDto.setProfile(profile.read(accountDetailsDto.getProfileId()).getBody());
        return accountDetailsDto;
    }

    /**
     * @param ids список технических идентификаторов {@link AccountDetailsEntity}.
     * @return список {@link AccountDetailsDto}
     */
    @Override
    public List<AccountDetailsDto> readAllById(List<Long> ids) {
        List<AccountDetailsEntity> accountDetailsList = repository.findAllById(ids);
        if (ids.size() > accountDetailsList.size()) {
            throw returnEntityNotFoundException("Одного или нескольких id из списка не найдено");
        }
        List<AccountDetailsDto> accountDetailsDtoList = mapper.toDtoList(accountDetailsList);
        for (AccountDetailsDto accountDetailsDto : accountDetailsDtoList) {
            accountDetailsDto.setProfile(profile.read(accountDetailsDto.getProfileId()).getBody());
        }
        return accountDetailsDtoList;
    }

    /**
     * @param accountDetailsDto {@link AccountDetailsDto}
     * @return {@link AccountDetailsDto}
     */
    @Override
    @Transactional
    public AccountDetailsDto create(AccountDetailsDto accountDetailsDto) {
        final AccountDetailsEntity accountDetails = repository.save(mapper.toEntity(accountDetailsDto));
        profile.create(accountDetailsDto.getProfile());
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
        profile.update(accountDetailsDto.getProfileId(), accountDetailsDto.getProfile());
        return mapper.toDto(accountDetails);
    }

    private EntityNotFoundException returnEntityNotFoundException(String massage) {
        return new EntityNotFoundException(massage);
    }
}
