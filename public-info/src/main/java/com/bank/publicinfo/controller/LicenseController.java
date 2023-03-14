package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;
import com.bank.publicinfo.service.LicenseService;
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
 * Controller для {@link LicenseEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/license")
public class LicenseController {

    private final LicenseService service;

    /**
     * @param id техничский идентификатор {@link LicenseEntity}
     * @return {@link ResponseEntity} c {@link LicenseDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<LicenseDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link LicenseEntity}
     * @return {@link ResponseEntity} c {@link LicenseDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<LicenseDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param license {@link LicenseDto}
     * @return {@link ResponseEntity} c {@link LicenseDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<LicenseDto> create(@RequestBody LicenseDto license) {
        return new ResponseEntity<>(service.save(license), HttpStatus.OK);
    }

    /**
     * @param id      технический идентификатор {@link LicenseEntity}
     * @param license {@link LicenseDto}
     * @return {@link ResponseEntity} c {@link LicenseDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<LicenseDto> update(@PathVariable("id") Long id, @RequestBody LicenseDto license) {
        return ResponseEntity.ok(service.update(id, license));
    }
}
