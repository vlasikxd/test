package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.service.RegistrationService;
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
 * Controller для {@link RegistrationEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService service;

    /**
     * @param id техничский идентификатор {@link RegistrationEntity}
     * @return {@link ResponseEntity} c {@link RegistrationDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    /**
     * @param ids список техничских идентификаторов {@link RegistrationEntity}
     * @return {@link ResponseEntity} c {@link RegistrationDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<RegistrationDto>> readAll(@RequestParam("id") List<Long> ids) {
        return new ResponseEntity<>(service.readAll(ids), HttpStatus.OK);
    }

    /**
     * @param registrationDto {@link RegistrationDto}
     * @return {@link ResponseEntity} c {@link RegistrationDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<RegistrationDto> create(@RequestBody RegistrationDto registrationDto) {
        return new ResponseEntity<>(service.save(registrationDto), HttpStatus.OK);
    }

    /**
     * @param id технический идентификатор {@link RegistrationEntity}
     * @param registration {@link RegistrationDto}
     * @return {@link ResponseEntity} c {@link RegistrationDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDto> update(@PathVariable("id") Long id,
                                                  @RequestBody RegistrationDto registration) {
        return ResponseEntity.ok(service.update(id, registration));
    }
}
