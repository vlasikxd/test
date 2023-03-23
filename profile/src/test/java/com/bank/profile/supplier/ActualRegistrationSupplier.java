package com.bank.profile.supplier;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;

public class ActualRegistrationSupplier extends ParentTest {

    public ActualRegistrationDto getDto(Long id, String city, Long index) {
        return new ActualRegistrationDto(id, WHITESPACE, WHITESPACE, city,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, index
        );
    }

    public ActualRegistrationEntity getEntity(Long id, String city, Long index) {
        return new ActualRegistrationEntity(id, WHITESPACE, WHITESPACE, city,
                WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, WHITESPACE, index
        );
    }
}
