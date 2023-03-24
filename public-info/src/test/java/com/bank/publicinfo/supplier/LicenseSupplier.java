package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.entity.LicenseEntity;
import org.junit.jupiter.api.Disabled;


public class LicenseSupplier {

    public LicenseDto getDto(Long id, Byte photo, BankDetailsDto bankDetails) {
        return new LicenseDto(id, photo, bankDetails);
    }

    public LicenseEntity getEntity(Long id, Byte photo, BankDetailsEntity bankDetails) {
        return new LicenseEntity(id, photo, bankDetails);
    }
}
