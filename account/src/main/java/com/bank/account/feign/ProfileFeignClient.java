package com.bank.account.feign;

import com.bank.account.dto.profileDto.ProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "profile-app", url = "http://localhost:8089/api/profile")
public interface ProfileFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<ProfileDto> read(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<ProfileDto>> readAll(@RequestParam("id") List<Long> ids);

    @PostMapping
    ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profileDto);

    @PutMapping("/{id}")
    ResponseEntity<ProfileDto> update(@PathVariable("id") Long id,
                                      @RequestBody ProfileDto profile);

}
