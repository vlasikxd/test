package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;


public class BankDetailsSupplier {

    public BankDetailsDto getDto(Long id, Long bik, Long inn, Long kpp) {
        return new BankDetailsDto(
                id, bik, inn, kpp,1 ,
                "testCity", "testJointCompany", "testName"
        );
    }

    public BankDetailsEntity getEntity(Long id, Long bik, Long inn, Long kpp) {
        return new BankDetailsEntity(
                id, bik, inn, kpp, 1,
                "testCity", "testJointCompany", "testName"
        );
    }
}
