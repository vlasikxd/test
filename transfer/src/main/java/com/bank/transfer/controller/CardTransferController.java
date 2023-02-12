package com.bank.transfer.controller;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.service.CardTransferService;
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
 * Контролер для {@link CardTransferDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardTransferController {

    private final CardTransferService service;

    /**
     * @param transfer {@link CardTransferDto}
     * @return {@link ResponseEntity<CardTransferDto>} HTTPStatus.OK.
     */
    @PostMapping("/create")
    public ResponseEntity<CardTransferDto> create(@RequestBody CardTransferDto transfer) {
        return new ResponseEntity<>(service.create(transfer), HttpStatus.OK);
    }

    /**
     * @param id технический идентификатор {@link CardTransferEntity}
     * @return {@link ResponseEntity<CardTransferDto>} HTTPStatus.OK.
     */
    @GetMapping("/read")
    public ResponseEntity<CardTransferDto> read(@RequestParam Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids лист технических идентификаторов {@link CardTransferEntity}
     * @return {@link ResponseEntity<List>} c {@link CardTransferDto}, HTTPStatus.OK.
     */
    @GetMapping("/read/all")
    public ResponseEntity<List<CardTransferDto>> readAll(@RequestParam List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param transfer {@link CardTransferDto}
     * @param id технический идентификатор {@link CardTransferEntity}
     * @return {@link ResponseEntity<CardTransferDto>} HTTPStatus.OK.
     */
    @PutMapping("{id}")
    public ResponseEntity<CardTransferDto> update(@RequestBody CardTransferDto transfer, @RequestParam Long id) {
        return new ResponseEntity<>(service.update(transfer, id), HttpStatus.OK);
    }
}
