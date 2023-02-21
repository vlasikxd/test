package com.bank.profile.supplier;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.entity.ProfileEntity;

public class AccountDetailsIdSupplier {

    public AccountDetailsIdDto getDto(Long id, Long accountId, ProfileDto profile) {
        return new AccountDetailsIdDto(id, accountId, profile);
    }

    public AccountDetailsIdEntity getEntity(Long id, Long accountId, ProfileEntity profile) {
        return new AccountDetailsIdEntity(id, accountId, profile);
    }
}
