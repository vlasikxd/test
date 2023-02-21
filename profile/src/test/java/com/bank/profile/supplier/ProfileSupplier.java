package com.bank.profile.supplier;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;

public class ProfileSupplier {

    public ProfileDto getDto(Long id, Long phoneNumber, String email, String nameOnCard, Long inn, Long snils,
                             PassportDto passport, ActualRegistrationDto actualRegistration) {

        return new ProfileDto(id, phoneNumber, email, nameOnCard, inn, snils, passport, actualRegistration);
    }

    public ProfileEntity getEntity(Long id, Long phoneNumber, String email, String nameOnCard, Long inn, Long snils,
                                   PassportEntity passport, ActualRegistrationEntity actualRegistration) {

        return new ProfileEntity(id, phoneNumber, email, nameOnCard, inn, snils, passport, actualRegistration);
    }
}
