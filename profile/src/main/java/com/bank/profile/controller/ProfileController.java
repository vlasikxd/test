package com.bank.profile.controller;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.service.ProfileService;
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
 * Controller для {@link ProfileEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "Контроллер банковского профиля", description = "API для получения информации о банковском профиле")
public class ProfileController {

    private final ProfileService service;

    /**
     * @param id техничский идентификатор {@link ProfileEntity}
     * @return {@link ResponseEntity} c {@link ProfileDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    @Operation(summary = "Найти данные банковского профиля по id")
    public ResponseEntity<ProfileDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link ProfileEntity}
     * @return {@link ResponseEntity} c {@link ProfileDto} и HttpStatus OK
     */
    @GetMapping
    @Operation(summary = "Найти данные банковских профилей по id")
    public ResponseEntity<List<ProfileDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param profileDto {@link ProfileDto}
     * @return {@link ResponseEntity} c {@link ProfileDto} и HttpStatus OK
     */
    @PostMapping
    @Operation(summary = "Создание банковского профиля")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profileDto) {
        return new ResponseEntity<>(service.save(profileDto), HttpStatus.OK);
    }

    /**
     * @param id технический идентификатор {@link ProfileEntity}
     * @param profile {@link ProfileDto}
     * @return {@link ResponseEntity} c {@link ProfileDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    @Operation(summary = "Изменение данных профиля пользователя по id")
    public ResponseEntity<ProfileDto> update(@PathVariable("id") Long id,
                                             @RequestBody ProfileDto profile) {
        return ResponseEntity.ok(service.update(id, profile));
    }
}

