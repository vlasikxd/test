package com.bank.account.dto.profileDto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassportDto {

    Long id;
    Integer series;
    Long number;
    String lastName;
    String firstName;
    String middleName;
    String gender;
    LocalDate birthDate;
    String birthPlace;
    String issuedBy;
    LocalDate dateOfIssue;
    Integer divisionCode;
    LocalDate expirationDate;
    RegistrationDto registration;
}
