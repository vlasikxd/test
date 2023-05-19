package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.transferDto.AccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Сервис для {@link SuspiciousAccountTransferEntity}
 */
public interface SuspiciousAccountTransferService {

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @return {@link SuspiciousAccountTransferDto}
     */
    SuspiciousAccountTransferDto create(SuspiciousAccountTransferDto transfer);

    /**
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    SuspiciousAccountTransferDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    List<SuspiciousAccountTransferDto> readAll(List<Long> ids);

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    SuspiciousAccountTransferDto update(SuspiciousAccountTransferDto transfer, Long id);

    /**
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link ResponseEntity} c {@link AccountTransferDto} и {@link HttpStatus}
     */
    ResponseEntity<AccountTransferDto> readTransfer(Long id);

    /**
     * @param ids список технических идентификаторов {@link SuspiciousAccountTransferEntity}
     * @return {@link ResponseEntity} со списком {@link AccountTransferDto} и {@link HttpStatus}
     */
    ResponseEntity<List<AccountTransferDto>> readAllTransfer(List<Long> ids);
}
