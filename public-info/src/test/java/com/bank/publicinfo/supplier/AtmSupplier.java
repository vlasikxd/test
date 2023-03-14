package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.entity.BranchEntity;

import java.time.LocalTime;

public class AtmSupplier {

    public AtmDto getDto(Long id, String address, LocalTime startOfWork,
                         LocalTime endOfWork, Boolean allHours, BranchDto branch) {
        return new AtmDto(id, address, startOfWork, endOfWork, allHours, branch);
    }

    public AtmEntity getEntity(Long id, String address, LocalTime startOfWork,
                               LocalTime endOfWork, Boolean allHours, BranchEntity branch) {
        return new AtmEntity(id, address, startOfWork, endOfWork, allHours, branch);
    }
}
