package com.bank.account.dto;

import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.util.serialization.deserializer.StrictBooleanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Dto для {@link AccountDetailsEntity}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailsDto {
    Long id;

    @NotNull(message = "passportId must not be null")
    @Positive(message = "passportId must be positive")
    Long passportId;

    @NotNull(message = "accountId must not be null")
    @Positive(message = "accountNumber must be positive")
    Long accountNumber;

    @NotNull(message = "bankDetailsId must not be null")
    @Positive(message = "bankDetailsId must be positive")
    Long bankDetailsId;

    @NotNull(message = "money must not be null")
    BigDecimal money;

    @JsonDeserialize(using = StrictBooleanDeserializer.class)
    Boolean negativeBalance;

    @NotNull(message = "profileId must not be null")
    @Positive(message = "profileId must be positive")
    Long profileId;
}
