package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/details")
@Tag(name = "AccountDetails", description = "An API for getting account details")
public class AccountDetailsController {

    private final AccountDetailsService service;

    /**
     * @param id техничский идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Operation(
            summary = "Find AccountDetails by ID",
            description = "Returns a single AccountDetails",
            tags = {"AccountDetails"})
    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Operation(
            summary = "Find multiple AccountDetails by ids",
            description = """
                    Multiple ids can be provided with comma separated strings or standard &.
                    Use /details?id=1,2,3 or /details?id=1&id=2&id=3 for testing
                    """,
            tags = {"AccountDetails"}
    )
    @GetMapping
    public ResponseEntity<List<AccountDetailsDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAllById(ids), HttpStatus.OK);
    }

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Operation(
            summary = "Add a new AccountDetails to the Profile",
            description = "Only single JSON can be provided per request",
            tags = {"AccountDetails"}
    )
    @PostMapping
    public ResponseEntity<AccountDetailsDto> create(@RequestBody AccountDetailsDto accountDetails) {
        return new ResponseEntity<>(service.create(accountDetails), HttpStatus.OK);
    }

    /**
     * @param id             технический идентификатор {@link AccountDetailsEntity}
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link ResponseEntity} c {@link AccountDetailsDto} и HttpStatus OK
     */
    @Operation(
            summary = "Update an existing AccountDetails",
            description = "Update an existing AccountDetails by id",
            tags = {"AccountDetails"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> update(@PathVariable Long id,
                                                    @RequestBody AccountDetailsDto accountDetails) {
        return new ResponseEntity<>(service.update(id, accountDetails), HttpStatus.OK);
    }
}
