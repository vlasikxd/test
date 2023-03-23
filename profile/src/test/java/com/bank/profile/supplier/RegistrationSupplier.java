package com.bank.profile.supplier;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;

public class RegistrationSupplier extends ParentTest {

    public RegistrationDto getDto(Long id, String city, Long index) {
        return new RegistrationDto(id, WHITESPACE, WHITESPACE, city, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, index
        );
    }

    public RegistrationEntity getEntity(Long id, String city, Long index) {
        return new RegistrationEntity(id, WHITESPACE, WHITESPACE, city, WHITESPACE, WHITESPACE,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, index
        );
    }
}
