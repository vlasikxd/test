package com.bank.publicinfo.supplier;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import org.junit.jupiter.api.Disabled;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class BranchSupplier {

    public BranchDto getDto(Long id, String address, Long phoneNumber,
                            String city) {
        return new BranchDto(id, address, phoneNumber, city, LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public BranchEntity genEntity(Long id, String address, Long phoneNumber,
                                  String city) {
        return new BranchEntity(id, address, phoneNumber, city, LocalTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
