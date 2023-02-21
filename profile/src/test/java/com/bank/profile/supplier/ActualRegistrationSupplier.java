package com.bank.profile.supplier;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;

public class ActualRegistrationSupplier {

    public ActualRegistrationDto getDto(Long id, String country, String region, String city, String district,
                                        String locality, String street, String houseNumber, String houseBlock,
                                        String flatNumber, Long index) {

        return new ActualRegistrationDto(id, country, region, city, district, locality, street, houseNumber,
                houseBlock, flatNumber, index
        );
    }

    public ActualRegistrationEntity getEntity(Long id, String country, String region, String city, String district,
                                              String locality, String street, String houseNumber, String houseBlock,
                                              String flatNumber, Long index) {

        return new ActualRegistrationEntity(id, country, region, city, district, locality, street, houseNumber,
                houseBlock, flatNumber, index
        );
    }
}
