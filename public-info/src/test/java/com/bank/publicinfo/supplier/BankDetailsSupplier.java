package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;

public class BankDetailsSupplier {

    public BankDetailsDto getDto(Long id, Long bik, Long inn, Long kpp,
                                 Integer corAccount, String city, String joinStockCompany, String name) {
        return new BankDetailsDto(id, bik, inn, kpp, corAccount, city, joinStockCompany, name);
    }

    public BankDetailsEntity getEntity(Long id, Long bik, Long inn, Long kpp,
                                       Integer corAccount, String city, String joinStockCompany, String name) {
        return new BankDetailsEntity(id, bik, inn, kpp, corAccount, city, joinStockCompany, name);
    }
}
