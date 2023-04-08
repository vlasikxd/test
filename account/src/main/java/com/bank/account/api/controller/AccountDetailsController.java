package com.bank.account.api.controller;

import com.bank.account.api.AccountDetailsApi;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.service.AccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller для {@link AccountDetailsEntity}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/details", produces = "application/json")
public class AccountDetailsController implements AccountDetailsApi {

    private final AccountDetailsService service;

    /**
     * @param id техничский идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<AccountDetailsDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAllById(ids), HttpStatus.OK);
    }

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<AccountDetailsDto> create(@RequestBody AccountDetailsDto accountDetails) {
        return new ResponseEntity<>(service.create(accountDetails), HttpStatus.OK);
    }

    /**
     * @param id             технический идентификатор {@link AccountDetailsEntity}
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @PutMapping( "/{id}")
    public ResponseEntity<AccountDetailsDto> update(@PathVariable Long id,
                                                    @RequestBody AccountDetailsDto accountDetails) {
        return new ResponseEntity<>(service.update(id, accountDetails), HttpStatus.OK);
    }
}
