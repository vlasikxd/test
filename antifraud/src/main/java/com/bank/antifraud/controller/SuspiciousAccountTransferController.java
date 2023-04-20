package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
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

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для {@link SuspiciousAccountTransferDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/suspicious/account/transfer")
@Tag(name = "Контроллер подозрительных переводов по номеру счёта")

public class SuspiciousAccountTransferController {

    private final SuspiciousAccountTransferService service;

    /**
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @return {@link ResponseEntity} c {@link SuspiciousAccountTransferDto} и {@link HttpStatus}
     */
    @PostMapping
    @Operation(summary = "Создание отчёта о статусе блокировки")
    public ResponseEntity<SuspiciousAccountTransferDto> create(@Valid
                                                               @RequestBody SuspiciousAccountTransferDto transfer) {
        return ResponseEntity.ok(service.create(transfer));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link ResponseEntity} c {@link SuspiciousAccountTransferDto} и {@link HttpStatus}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение статуса блокировки по id")
    public ResponseEntity<SuspiciousAccountTransferDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousAccountTransferEntity}
     * @return {@link ResponseEntity} со списком {@link SuspiciousAccountTransferDto} и {@link HttpStatus}
     */
    @GetMapping
    @Operation(summary = "Получение всех отчётов")
    public ResponseEntity<List<SuspiciousAccountTransferDto>> readAll(@RequestParam("id") List<Long> ids) {
        return ResponseEntity.ok(service.readAll(ids));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @param transfer {@link SuspiciousAccountTransferDto}
     * @return {@link ResponseEntity} c {@link SuspiciousAccountTransferDto} и {@link HttpStatus}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновление статуса блокировки по id")
    public ResponseEntity<SuspiciousAccountTransferDto> update(@Valid @PathVariable("id") Long id,
                                                               @RequestBody SuspiciousAccountTransferDto transfer) {
        return ResponseEntity.ok(service.update(transfer, id));
    }
}
