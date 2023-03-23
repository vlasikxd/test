package com.bank.profile.supplier;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;

public class ProfileSupplier extends ParentTest {

    public ProfileDto getDto(Long id, Long phoneNumber, String email, String nameOnCard) {
        return new ProfileDto(id, phoneNumber, email, nameOnCard, TWO, TWO, null, null);
    }

    public ProfileEntity getEntity(Long id, Long phoneNumber, String email, String nameOnCard) {
        return new ProfileEntity(id, phoneNumber, email, nameOnCard, TWO, TWO, null, null);
    }
}
