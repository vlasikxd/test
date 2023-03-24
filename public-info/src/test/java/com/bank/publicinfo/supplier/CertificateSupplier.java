package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.entity.CertificateEntity;
import org.junit.jupiter.api.Disabled;


public class CertificateSupplier {

    public CertificateDto getDto(Long id, Byte photo, BankDetailsDto bankDetails) {
        return new CertificateDto(id, photo, bankDetails);
    }

    public CertificateEntity getEntity(Long id, Byte photo, BankDetailsEntity bankDetails) {
        return new CertificateEntity(id, photo, bankDetails);
    }
}
