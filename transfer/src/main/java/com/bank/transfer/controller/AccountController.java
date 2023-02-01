package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.service.AccountService;
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
 * Контролер для {@link AccountTransferDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService service;

    /**
     * @param transfer {@link AccountTransferDto}
     * @return {@link ResponseEntity<AccountTransferDto>} HTTPStatus.OK.
    */
    @PostMapping("/create")
    public ResponseEntity<AccountTransferDto> create(@RequestBody AccountTransferDto transfer) {
        return new ResponseEntity<>(service.create(transfer), HttpStatus.OK);
    }

    /**
     * @param id технический идентификатор {@link AccountTransferEntity}
     * @return {@link ResponseEntity<AccountTransferDto>} HTTPStatus.OK.
     */
    @GetMapping("/read")
    public ResponseEntity<AccountTransferDto> read(@RequestParam Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids лист технических идентификаторов {@link AccountTransferEntity}
     * @return {@link ResponseEntity<List>} c {@link AccountTransferDto}, HTTPStatus.OK.
     */
    // TODO "/read/" измени на "/read/all"
    @GetMapping("/read/")
    public ResponseEntity<List<AccountTransferDto>> readAll(@RequestParam List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param transfer {@link AccountTransferDto}
     * @param id технический идентификатор {@link AccountTransferEntity}
     * @return {@link ResponseEntity<AccountTransferDto>} HTTPStatus.OK.
     */
    @PutMapping("/update")
    public ResponseEntity<AccountTransferDto> update(@RequestBody AccountTransferDto transfer, Long id) {
        return new ResponseEntity<>(service.update(transfer, id), HttpStatus.OK);
    }
}
