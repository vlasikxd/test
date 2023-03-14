package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import com.bank.publicinfo.service.CertificateService;
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
 * Controller для {@link CertificateEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/certificate")
public class CertificateController {

    private final CertificateService service;

    /**
     * @param id техничский идентификатор {@link CertificateEntity}
     * @return {@link ResponseEntity} c {@link CertificateDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link CertificateEntity}
     * @return {@link ResponseEntity} c {@link CertificateDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<CertificateDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param certificate {@link CertificateDto}
     * @return {@link ResponseEntity} c {@link CertificateDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<CertificateDto> create(@RequestBody CertificateDto certificate) {
        return new ResponseEntity<>(service.save(certificate), HttpStatus.OK);
    }

    /**
     * @param id          технический идентификатор {@link CertificateEntity}
     * @param certificate {@link CertificateDto}
     * @return {@link ResponseEntity} c {@link CertificateDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<CertificateDto> update(@PathVariable("id") Long id, @RequestBody CertificateDto certificate) {
        return ResponseEntity.ok(service.update(id, certificate));
    }
}
