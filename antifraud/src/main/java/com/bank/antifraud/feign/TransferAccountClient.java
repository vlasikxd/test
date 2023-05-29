package com.bank.antifraud.feign;

import com.bank.antifraud.dto.transferDto.AccountTransferDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "transfer-account", url = "http://localhost:8092/api/transfer")
public interface TransferAccountClient {

    @Cacheable(value = "transfer-cache")
    @GetMapping("/account/read")
    ResponseEntity<AccountTransferDto> read(@RequestParam Long id);
}
