package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.transferDto.CardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Сервис для {@link SuspiciousCardTransferEntity}
 */
public interface SuspiciousCardTransferService {

    /**
     * @param transfer {@link SuspiciousCardTransferDto}
     * @return {@link SuspiciousCardTransferDto}
     */
    SuspiciousCardTransferDto create(SuspiciousCardTransferDto transfer);

    /**
     * @param id технический идентификатор {@link SuspiciousCardTransferEntity}
     * @return {@link SuspiciousCardTransferDto}
     */
    SuspiciousCardTransferDto read(Long id);

    /**
     * @param ids список технических идентификаторов {@link SuspiciousCardTransferEntity}
     * @return {@link SuspiciousCardTransferDto}
     */
    List<SuspiciousCardTransferDto> readAll(List<Long> ids);

    /**
     * @param transfer {@link SuspiciousCardTransferDto}
     * @param id технический идентификатор {@link SuspiciousCardTransferEntity}
     * @return {@link SuspiciousCardTransferDto}
     */
    SuspiciousCardTransferDto update(SuspiciousCardTransferDto transfer, Long id);

    /**
     * @param id технический идентификатор {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity} c {@link CardTransferDto} и {@link HttpStatus}
     */
    ResponseEntity<CardTransferDto> readTransfer(Long id);

    /**
     * @param ids список технических идентификаторов {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity} со списком {@link CardTransferDto} и {@link HttpStatus}
     */
    ResponseEntity<List<CardTransferDto>> readAllTransfer(List<Long> ids);
}
