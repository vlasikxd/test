package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;


public class BankDetailsSupplier {

    public BankDetailsDto getDto(Long id, String bik, String inn, String kpp) {
        return new BankDetailsDto(
                id, bik, inn, kpp, "30101810400000000225",
                "testCity", "ПАО \"Сбербанк\"", "testName"
        );
    }

    public BankDetailsEntity getEntity(Long id, String bik, String inn, String kpp) {
        return new BankDetailsEntity(
                id, bik, inn, kpp, "30101810400000000225",
                "testCity", "ПАО \"Сбербанк\"", "testName"
        );
    }
}
