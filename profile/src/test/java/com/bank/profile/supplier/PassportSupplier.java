package com.bank.profile.supplier;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;

import java.time.LocalDate;

public class PassportSupplier extends ParentTest {

    public PassportDto getDto(Long id, String lastName, String firstName,
                              LocalDate birthDate, RegistrationDto registration) {
        return new PassportDto(id, INT_ONE, TWO, lastName, firstName, WHITESPACE, WHITESPACE, birthDate,
                WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, registration
        );
    }

    public PassportEntity getEntity(Long id, String lastName, String firstName,
                                    LocalDate birthDate, RegistrationEntity registration) {
        return new PassportEntity(id, INT_ONE, TWO, lastName, firstName, WHITESPACE, WHITESPACE, birthDate,
                WHITESPACE, WHITESPACE, LOCAL_DATE, INT_ONE, LOCAL_DATE, registration
        );
    }
}
