package com.bank.profile.controller;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.service.AccountDetailsIdService;
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
 * Controller для {@link AccountDetailsIdEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/account/details-id")
public class AccountDetailsIdController {

    private final AccountDetailsIdService service;

    /**
     * @param id технический идентификатор {@link AccountDetailsIdEntity}
     * @return {@link ResponseEntity} c {@link AccountDetailsIdDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsIdDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link AccountDetailsIdEntity}
     * @return {@link ResponseEntity} c {@link AccountDetailsIdDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<AccountDetailsIdDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param accountDetailsId {@link AccountDetailsIdDto}
     * @return {@link ResponseEntity} c {@link AccountDetailsIdDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<AccountDetailsIdDto> create(@RequestBody AccountDetailsIdDto accountDetailsId) {
        return new ResponseEntity<>(service.save(accountDetailsId), HttpStatus.OK);
    }

    /**
     * @param id технический идентификатор {@link AccountDetailsIdEntity}
     * @param accountDetailsId {@link AccountDetailsIdDto}
     * @return {@link ResponseEntity} c {@link AccountDetailsIdDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccountDetailsIdDto> update(@PathVariable("id") Long id,
                                                      @RequestBody AccountDetailsIdDto accountDetailsId) {
        return ResponseEntity.ok(service.update(id, accountDetailsId));
    }
}

