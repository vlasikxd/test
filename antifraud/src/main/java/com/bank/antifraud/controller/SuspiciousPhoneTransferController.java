package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.dto.transferDto.PhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.feign.TransferPhoneClient;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
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
 * Контроллер для {@link SuspiciousPhoneTransferDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/suspicious/phone/transfer")
@Tag(name = "Контроллер подозрительных переводов по номеру телефона")
public class SuspiciousPhoneTransferController {

    private final SuspiciousPhoneTransferService service;
    private final TransferPhoneClient transferClient;

    /**
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @return {@link ResponseEntity} c {@link SuspiciousPhoneTransferDto} и {@link HttpStatus}
     */
    @Operation(summary = "Создание отчёта о статусе блокировки")
    @PostMapping
    public ResponseEntity<SuspiciousPhoneTransferDto> create(@Valid @RequestBody SuspiciousPhoneTransferDto transfer) {
        return ResponseEntity.ok(service.create(transfer));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} c {@link SuspiciousPhoneTransferDto} и {@link HttpStatus}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение статуса блокировки по id")
    public ResponseEntity<SuspiciousPhoneTransferDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} со списком {@link SuspiciousPhoneTransferDto} и {@link HttpStatus}
     */
    @GetMapping
    @Operation(summary = "Получение всех отчётов")
    public ResponseEntity<List<SuspiciousPhoneTransferDto>> readAll(@RequestParam("id") List<Long> ids) {
        return ResponseEntity.ok(service.readAll(ids));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @param transfer {@link SuspiciousPhoneTransferDto}
     * @return {@link ResponseEntity} c {@link SuspiciousPhoneTransferDto} и {@link HttpStatus}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновление статуса блокировки по id")
    public ResponseEntity<SuspiciousPhoneTransferDto> update(@Valid @PathVariable("id") Long id,
                                                               @RequestBody SuspiciousPhoneTransferDto transfer) {
        return ResponseEntity.ok(service.update(transfer, id));
    }

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} c {@link PhoneTransferDto} и {@link HttpStatus}
     */
    @GetMapping("/{id}/info")
    @Operation(summary = "Получение информации о переводе")
    public ResponseEntity<PhoneTransferDto> readTransfer(@PathVariable("id") Long id) {
        Long phoneTransferId = service.read(id).getPhoneTransferId();
        return transferClient.read(phoneTransferId);
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} со списком {@link PhoneTransferDto} и {@link HttpStatus}
     */
    @GetMapping("/info")
    @Operation(summary = "Получение информации о всех отчетах")
    public ResponseEntity <List<PhoneTransferDto>> readAllTransfer(@RequestParam("id") List<Long> ids) {
        List<Long> cardTransferIds = service.readAll(ids).stream().map(SuspiciousPhoneTransferDto::getPhoneTransferId).toList();
        return transferClient.readAll(cardTransferIds);
    }
}
