package com.bank.antifraud.feign;

import com.bank.antifraud.dto.transferDto.CardTransferDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "transfer-card", url = "http://localhost:8092/api/transfer")
public interface TransferCardClient {

    @Cacheable(value = "transfer-cache")
    @GetMapping("/card/read")
    ResponseEntity<CardTransferDto> read(@RequestParam Long id);
}
