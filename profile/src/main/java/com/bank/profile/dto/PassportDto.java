package com.bank.profile.dto;

import com.bank.profile.entity.PassportEntity;
import java.time.LocalDate;

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
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

/**
 * Dto для {@link PassportEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassportDto {

    Long id;

    @NotNull
    @Range(min = 1000, max = 9999, message = "Серия должна содержать 4 цифры")
    Integer series;

    @NotNull
    @Range(min = 100000L, max = 999999L, message = "Номер должен содержать 6 цифр")
    Long number;

    @NotBlank
    @Size(min = 2, max = 15, message = "Фамилия пользователя должна содержать от 2 до 15 символов")
    String lastName;

    @NotBlank
    @Size(min = 2, max = 15, message = "Имя пользователя должно содержать от 2 до 15 символов")
    String firstName;

    @NotBlank
    @Size(min = 2, max = 15, message = "Отчество пользователя должно содержать от 2 до 15 символов")
    String middleName;

    @NotBlank
    @Pattern(regexp = "(?:муж|МУЖ|Муж|жен|ЖЕН|Жен)$")
    String gender;

    @Past
    @NotNull
    LocalDate birthDate;

    @NotBlank
    String birthPlace;

    @NotBlank
    String issuedBy;

    @Past
    @NotNull
    LocalDate dateOfIssue;

    @NotNull
    @Range(min = 100000, max = 999999, message = "Код подразделения должен содержать 6 цифр")
    Integer divisionCode;

    @Past
    @NotNull
    LocalDate expirationDate;

    RegistrationDto registration;
}

