package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для {@link SuspiciousPhoneTransferDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/suspicious/phone/transfer")
public class SuspiciousPhoneTransferController {

    private final SuspiciousPhoneTransferService service;

    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @return {@link ResponseEntity} c {@link SuspiciousPhoneTransferDto} и {@link HttpStatus}
     */
    @PostMapping
    public ResponseEntity<SuspiciousPhoneTransferDto> create(
            @RequestBody @Valid SuspiciousPhoneTransferDto transfer) {
        return ResponseEntity.ok(service.create(transfer));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} c {@link SuspiciousPhoneTransferDto} и {@link HttpStatus}
     */
    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousPhoneTransferDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} со списком {@link SuspiciousPhoneTransferDto} и {@link HttpStatus}
     */
    @GetMapping
    public ResponseEntity<List<SuspiciousPhoneTransferDto>> readAll(@RequestParam("id") List<Long> ids) {
        return ResponseEntity.ok(service.readAll(ids));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @return {@link ResponseEntity} c {@link SuspiciousPhoneTransferDto} и {@link HttpStatus}
     */
    @PutMapping("/{id}")
    public ResponseEntity<SuspiciousPhoneTransferDto> update(@PathVariable("id") Long id,
                                                               @RequestBody SuspiciousPhoneTransferDto transfer) {
        return ResponseEntity.ok(service.update(transfer, id));
    }
}
