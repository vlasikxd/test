package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.service.AtmService;
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
 * Controller для {@link AtmEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/atm")
public class AtmController {

    private final AtmService service;

    /**
     * @param id техничский идентификатор {@link AtmEntity}
     * @return {@link ResponseEntity} c {@link AtmDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<AtmDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids список технических идентификаторов {@link AtmEntity}
     * @return {@link ResponseEntity} c листом {@link AtmDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<AtmDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param atm {@link AtmDto}
     * @return {@link ResponseEntity} c {@link AtmDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<AtmDto> create(@RequestBody AtmDto atm) {
        return new ResponseEntity<>(service.save(atm), HttpStatus.OK);
    }

    /**
     * @param id  технический идентификатор {@link AtmEntity}
     * @param atm {@link AtmDto}
     * @return {@link ResponseEntity} c {@link AtmDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<AtmDto> update(@PathVariable("id") Long id, @RequestBody AtmDto atm) {
        return ResponseEntity.ok(service.update(id, atm));
    }
}
