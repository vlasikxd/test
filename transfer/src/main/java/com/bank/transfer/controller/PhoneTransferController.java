package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.service.PhoneTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * Контролер для {@link PhoneTransferDto}
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/phone")
public class PhoneTransferController {

    private final PhoneTransferService service;

    /**
     * @param transfer {@link PhoneTransferDto}
     * @return {@link ResponseEntity<PhoneTransferDto>} HTTPStatus.OK.
     */
    @PostMapping("/create")
    public ResponseEntity<PhoneTransferDto> create(@RequestBody PhoneTransferDto transfer) {
        return new ResponseEntity<>(service.create(transfer), HttpStatus.OK);
    }

    /**
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return {@link ResponseEntity<PhoneTransferDto>} HTTPStatus.OK.
     */
    @GetMapping("/read")
    public ResponseEntity<PhoneTransferDto> read(@RequestParam Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids лист технических идентификаторов {@link PhoneTransferEntity}
     * @return {@link ResponseEntity<List>} c {@link PhoneTransferDto}, HTTPStatus.OK.
     */

    @GetMapping("/read/all")
    public ResponseEntity<List<PhoneTransferDto>> readAll(@RequestParam List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param transfer {@link PhoneTransferDto}
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return {@link ResponseEntity<PhoneTransferDto>} HTTPStatus.OK.
     */
    @PutMapping("/update")
    public ResponseEntity<PhoneTransferDto> update(@RequestBody PhoneTransferDto transfer, Long id) {
        return new ResponseEntity<>(service.update(transfer, id), HttpStatus.OK);
    }
}
