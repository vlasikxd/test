package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.service.PassportService;
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
 * Controller для {@link PassportEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/passport")
@Tag(name = "Паспорт контроллер", description = "API для получения паспортных данных пользователей")
public class PassportController {

    private final PassportService service;

    /**
     * @param id техничский идентификатор {@link PassportEntity}
     * @return {@link ResponseEntity} c {@link PassportDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    @Operation(summary = "Найти паспортные данные пользователя по id")
    public ResponseEntity<PassportDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link PassportEntity}
     * @return {@link ResponseEntity} c {@link PassportDto} и HttpStatus OK
     */
    @GetMapping
    @Operation(summary = "Найти паспортные данные пользователей по id")
    public ResponseEntity<List<PassportDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param passportDto {@link PassportDto}
     * @return {@link ResponseEntity} c {@link PassportDto} и HttpStatus OK
     */
    @PostMapping
    @Operation(summary = "Запись паспортных данных пользователя в бд")
    public ResponseEntity<PassportDto> create(@RequestBody PassportDto passportDto) {
        return new ResponseEntity<>(service.save(passportDto), HttpStatus.OK);
    }

    /**
     * @param id технический идентификатор {@link PassportEntity}
     * @param passport {@link PassportDto}
     * @return {@link ResponseEntity} c {@link PassportDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    @Operation(summary = "Изменение паспортных данных пользователя по id")
    public ResponseEntity<PassportDto> update(@PathVariable("id") Long id,
                                              @RequestBody PassportDto passport) {
        return ResponseEntity.ok(service.update(id, passport));
    }
}

