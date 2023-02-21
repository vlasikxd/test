package com.bank.profile.supplier;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;

public class RegistrationSupplier {

    public RegistrationDto getDto(Long id, String country, String region, String city, String district,
                                  String locality, String street, String houseNumber, String houseBlock,
                                  String flatNumber, Long index) {

        return new RegistrationDto(id, country, region, city, district, locality, street, houseNumber, houseBlock,
                flatNumber, index);
    }

    public RegistrationEntity getEntity(Long id, String country, String region, String city, String district,
                                        String locality, String street, String houseNumber, String houseBlock,
                                        String flatNumber, Long index) {

        return new RegistrationEntity(id, country, region, city, district, locality, street, houseNumber, houseBlock,
                flatNumber, index);
    }
}
