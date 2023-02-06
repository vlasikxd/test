package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.service.ActualRegistrationService;
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
 * Controller для {@link ActualRegistrationEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/actual_registration")
public class ActualRegistrationController {

    private final ActualRegistrationService service;

    /**
     * @param id техничский идентификатор {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity} c {@link ActualRegistrationDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActualRegistrationDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    /**
     * @param ids список техничских идентификаторов {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity} c {@link ActualRegistrationDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<ActualRegistrationDto>> readAll(@RequestParam("id") List<Long> ids) {
        return ResponseEntity.ok(service.readAll(ids));
    }

    /**
     * @param actualRegistration {@link ActualRegistrationDto}
     * @return {@link ResponseEntity} c {@link ActualRegistrationDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<ActualRegistrationDto> create(@RequestBody ActualRegistrationDto actualRegistration) {
        return ResponseEntity.ok(service.save(actualRegistration));
    }

    /**
     * @param id технический идентификатор {@link ActualRegistrationEntity}
     * @param actualRegistration {@link ActualRegistrationDto}
     * @return {@link ResponseEntity} c {@link ActualRegistrationDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActualRegistrationDto> update(@PathVariable("id") Long id,
                                                        @RequestBody ActualRegistrationDto actualRegistration) {
        return ResponseEntity.ok(service.update(id, actualRegistration));
    }
}

