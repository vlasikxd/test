package com.bank.profile.dto;

import com.bank.profile.entity.RegistrationEntity;
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
 * Dto для {@link RegistrationEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationDto {

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
    @Range(min = 100000L, max = 999999L, message = "Индекс должен содержать 6 символов")
    Long index;
}
