package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;

import java.time.LocalTime;

public class BranchSupplier {

    public BranchDto getDto(Long id, String address, Long phoneNumber,
                            String city, LocalTime startOfWork, LocalTime endOfWork) {
        return new BranchDto(id, address, phoneNumber, city, startOfWork, endOfWork);
    }

    public BranchEntity genEntity(Long id, String address, Long phoneNumber,
                                  String city, LocalTime startOfWork, LocalTime endOfWork) {
        return new BranchEntity(id, address, phoneNumber, city, startOfWork, endOfWork);
    }
}
