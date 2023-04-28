package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для {@link SuspiciousCardTransferDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/suspicious/card/transfer")
@Tag(name = "Контроллер подозрительных переводов по номеру карты")
public class SuspiciousCardTransferController {

    private final SuspiciousCardTransferService service;

    /**
     * @param transfer {@link SuspiciousCardTransferDto}
     * @return {@link ResponseEntity} c {@link SuspiciousCardTransferDto} и {@link HttpStatus}
     */
    @PostMapping
    @Operation(summary = "Создание отчёта о статусе блокировки")
    public ResponseEntity<SuspiciousCardTransferDto> create(@Valid @RequestBody SuspiciousCardTransferDto transfer) {
        return ResponseEntity.ok(service.create(transfer));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity} c {@link SuspiciousCardTransferDto} и {@link HttpStatus}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение статуса блокировки по id")
    public ResponseEntity<SuspiciousCardTransferDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity} со списком {@link SuspiciousCardTransferDto} и {@link HttpStatus}
     */
    @GetMapping
    @Operation(summary = "Получение всех отчётов")
    public ResponseEntity<List<SuspiciousCardTransferDto>> readAll(@RequestParam("id") List<Long> ids) {
        return ResponseEntity.ok(service.readAll(ids));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousCardTransferEntity}
     * @param transfer {@link SuspiciousCardTransferDto}
     * @return {@link ResponseEntity} c {@link SuspiciousCardTransferDto} и {@link HttpStatus}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновление статуса блокировки по id ")
    public ResponseEntity<SuspiciousCardTransferDto> update(@Valid @PathVariable("id") Long id,
                                                               @RequestBody SuspiciousCardTransferDto transfer) {
        return ResponseEntity.ok(service.update(transfer, id));
    }
}
