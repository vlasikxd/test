package com.bank.history.controller;

import java.util.List;

import com.bank.history.entity.HistoryEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.bank.history.dto.HistoryDto;
import org.springframework.http.HttpStatus;
import com.bank.history.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controller для {@link HistoryEntity}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
@Tag(name = "HistoryController", description = "Контроллер микросервиса по сбору истории из других микросервисов")
public class HistoryController {
    private final HistoryService service;

    /**
     * @param id технический идентификатор {@link HistoryEntity}
     * @return {@link ResponseEntity} c {@link HistoryDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    @Operation(summary = "Поиск информации об истории по id")
    public ResponseEntity<HistoryDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    /**
     * @param id список технических идентификаторов {@link HistoryEntity}
     * @return {@link ResponseEntity} c {@link HistoryDto} и HttpStatus OK
     */
    @GetMapping
    @Operation(summary = "Поиск информации об историях по id")
    public ResponseEntity<List<HistoryDto>> readAll(@RequestParam("id") List<Long> id) {
        return new ResponseEntity<>(service.readAllById(id), HttpStatus.OK);
    }

    /**
     * @param history {@link HistoryDto}
     * @return {@link ResponseEntity} c {@link HistoryDto} и HttpStatus OK
     */
    @PostMapping
    @Operation(summary = "Создание новой истории")
    public ResponseEntity<HistoryDto> create(@RequestBody HistoryDto history) {
        return new ResponseEntity<>(service.create(history), HttpStatus.OK);
    }

    /**
     * @param id      технический идентификатор {@link HistoryEntity}
     * @param history {@link HistoryDto}
     * @return {@link ResponseEntity} c {@link HistoryDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновление информации об истории по id")
    public ResponseEntity<HistoryDto> update(@PathVariable Long id,
                                             @RequestBody HistoryDto history) {
        return new ResponseEntity<>(service.update(id, history), HttpStatus.OK);
    }
}
