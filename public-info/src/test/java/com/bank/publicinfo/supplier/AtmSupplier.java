package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.entity.BranchEntity;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AtmSupplier {

    public AtmDto getDto(Long id, String address, Boolean allHours, BranchDto branch) {
        return new AtmDto(id, address,
                LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                allHours,
                branch
        );
    }

    public AtmEntity getEntity(Long id, String address, Boolean allHours, BranchEntity branch) {
        return new AtmEntity(id, address,
                LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                allHours,
                branch
        );
    }
}
