package com.bank.profile.dto;

import com.bank.profile.entity.AccountDetailsIdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Dto для {@link AccountDetailsIdEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActualRegistrationDto {

    Long id;

    @NotBlank
    String country;

    @NotBlank
    String region;

    @NotBlank
    String city;

    @NotBlank
    String district;

    @NotBlank
    String locality;

    @NotBlank
    String street;

    @NotBlank
    String houseNumber;

    @NotBlank
    String houseBlock;

    @NotBlank
    String flatNumber;

    @NotNull
    @Range(min = 100000L, max = 999999L, message = "Индекс должен содержать 6 цифр")
    Long index;
}

