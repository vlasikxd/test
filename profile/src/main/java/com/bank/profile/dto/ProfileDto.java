package com.bank.profile.dto;

import com.bank.profile.entity.ProfileEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

/**
 * Dto для {@link ProfileEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDto {

    Long id;

    @NotNull
    @Range(min = 1000000000L, max = 9999999999L, message = "Номер должен состоять из 10 цифр")
    Long phoneNumber;

    @NotBlank
    @Email(message = "Email не соответствует формату")
    String email;

    @NotBlank
    @Size(min = 2, max = 15, message = "Имя карты должно содержать от 2 до 15 символов")
    String nameOnCard;

    @NotNull
    @Range(min = 100000000000L, max = 999999999999L, message = "ИНН должен содержать 12 цифр")
    Long inn;

    @NotNull
    @Range(min = 10000000000L, max = 99999999999L, message = "СНИЛС должен содержать 11 цифр")
    Long snils;

    PassportDto passport;

    ActualRegistrationDto actualRegistration;
}

