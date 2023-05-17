package com.bank.antifraud.feign;

import com.bank.antifraud.dto.transferDto.AccountTransferDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Cacheable(value = "transfer-cache")
@FeignClient(value = "transfer-account", url = "http://localhost:8092/api/transfer")
public interface TransferAccountClient {

    @GetMapping("/account/read")
    ResponseEntity<AccountTransferDto> read(@RequestParam Long id);

    @GetMapping("/account/read/all")
    ResponseEntity<List<AccountTransferDto>> readAll(@RequestParam List<Long> ids);

}
