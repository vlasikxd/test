package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.service.BranchService;
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
 * Controller для {@link BranchEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/branch")
public class BranchController {

    private final BranchService service;

    /**
     * @param id технический идентификатор {@link BranchEntity}
     * @return {@link ResponseEntity} c {@link BranchDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(service.read(id));
    }

    /**
     * @param ids список техничских идентификаторов {@link BranchEntity}
     * @return {@link ResponseEntity} c {@link BranchDto} и HttpStatus OK
     */
    @GetMapping
    public ResponseEntity<List<BranchDto>> readAll(@RequestParam("id") List<Long> ids) {
        return ResponseEntity.ok(service.readAll(ids));
    }

    /**
     * @param branch {@link BranchDto}
     * @return {@link ResponseEntity} c {@link BranchDto} и HttpStatus OK
     */
    @PostMapping
    public ResponseEntity<BranchDto> create(@RequestBody BranchDto branch) {
        return ResponseEntity.ok(service.save(branch));
    }

    /**
     * @param id     технический идентификатор {@link BranchEntity}
     * @param branch {@link BranchDto}
     * @return {@link ResponseEntity} c {@link BranchDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> update(@PathVariable("id") Long id, @RequestBody BranchDto branch) {
        return ResponseEntity.ok(service.update(id, branch));
    }
}
