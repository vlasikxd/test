package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.service.BankDetailsService;
import lombok.RequiredArgsConstructor;
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
 * Controller для {@link BankDetailsEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-details")
public class BankDetailsController {

    private final BankDetailsService service;

    /**
     * @param id технический идентификатор {@link BankDetailsEntity}
     * @return {@link ResponseEntity} c {@link BankDetailsDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankDetailsDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    /**
     * @param ids список технических идентификаторов {@link BankDetailsEntity}
     * @return {@link ResponseEntity} c листом {@link BankDetailsDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<BankDetailsDto>> readAll(@RequestParam("id") List<Long> ids) {
        return ResponseEntity.ok(service.readAll(ids));
    }

    /**
     * @param bankDetails {@link BankDetailsDto}
     * @return {@link ResponseEntity} c {@link BankDetailsDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<BankDetailsDto> create(@RequestBody BankDetailsDto bankDetails) {
        return ResponseEntity.ok(service.save(bankDetails));
    }

    /**
     * @param id          технический идентификатор {@link BankDetailsEntity}
     * @param bankDetails {@link BankDetailsDto}
     * @return {@link ResponseEntity} c {@link BankDetailsDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<BankDetailsDto> update(@PathVariable("id") Long id, @RequestBody BankDetailsDto bankDetails) {
        return ResponseEntity.ok(service.update(id, bankDetails));
    }
}
