package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;

import java.util.List;

/**
 * Service для {@link CertificateDto}
 */
public interface CertificateService {

    /**
     * @param id технический идентификатор {@link CertificateEntity}
     * @return {@link CertificateDto}
     */
    CertificateDto read(Long id);

    /**
     * @param ids список технических индентификаторов {@link CertificateEntity}
     * @return список {@link CertificateDto}
     */
    List<CertificateDto> readAll(List<Long> ids);

    /**
     * @param certificate {@link CertificateDto}
     * @return {@link CertificateDto}
     */
    CertificateDto save(CertificateDto certificate);

    /**
     * @param id          технический идентификатор {@link CertificateEntity}
     * @param certificate {@link CertificateDto}
     * @return {@link CertificateDto}
     */
    CertificateDto update(Long id, CertificateDto certificate);
}
