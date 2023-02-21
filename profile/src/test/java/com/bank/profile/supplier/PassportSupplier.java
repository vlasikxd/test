package com.bank.profile.supplier;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;

import java.time.LocalDate;

public class PassportSupplier {

    public PassportDto getDto(Long id, Integer series, Long number, String lastName, String firstName,
                              String middleName, String gender, LocalDate birthDate, String birthPlace,
                              String issuedBy, LocalDate dateOfIssue, Integer divisionCode,
                              LocalDate expirationDate, RegistrationDto registration) {

        return new PassportDto(id, series, number, lastName, firstName, middleName, gender, birthDate, birthPlace,
                issuedBy, dateOfIssue, divisionCode, expirationDate, registration);
    }

    public PassportEntity getEntity(Long id, Integer series, Long number, String lastName, String firstName,
                                    String middleName, String gender, LocalDate birthDate, String birthPlace,
                                    String issuedBy, LocalDate dateOfIssue, Integer divisionCode,
                                    LocalDate expirationDate, RegistrationEntity registration) {

        return new PassportEntity(id, series, number, lastName, firstName, middleName, gender, birthDate, birthPlace,
                issuedBy, dateOfIssue, divisionCode, expirationDate, registration);
    }
}
