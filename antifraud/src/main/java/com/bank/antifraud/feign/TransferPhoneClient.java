package com.bank.antifraud.feign;

import com.bank.antifraud.dto.transferDto.PhoneTransferDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Cacheable(value = "transfer-cache")
@FeignClient(value = "transfer-phone", url = "http://localhost:8092/api/transfer")
public interface TransferPhoneClient {

    @GetMapping("/phone/read")
    ResponseEntity<PhoneTransferDto> read(@RequestParam Long id);
}
