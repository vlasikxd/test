package com.bank.profile.dto;

import com.bank.profile.entity.AccountDetailsIdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * Dto для {@link AccountDetailsIdEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailsIdDto {

    Long id;
    Long accountId;
    ProfileDto profile;
}

