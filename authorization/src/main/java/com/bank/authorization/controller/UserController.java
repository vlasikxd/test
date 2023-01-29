package com.bank.authorization.controller;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
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
 * Контролер для {@link UserDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    /**
     * @param id технический идентификатор {@link UserEntity}.
     * @return {@link ResponseEntity<UserDto>}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    /**
     * @param ids список технических идентификаторов {@link UserEntity}.
     * @return {@link ResponseEntity} c {@link List<UserDto>}.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> readAll(@RequestParam("id") List<Long> ids) {
        return ResponseEntity.ok(service.readAll(ids));
    }

    /**
     * @param user {@link UserDto}.
     * @return {@link ResponseEntity<UserDto>}.
     */
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        return ResponseEntity.ok(service.save(user));
    }

    /**
     * @param id технический идентификатор {@link UserEntity}.
     * @param user {@link UserDto}.
     * @return {@link ResponseEntity<UserDto>}.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") Long id, @RequestBody UserDto user) {
        return ResponseEntity.ok(service.update(id, user));
    }
}
