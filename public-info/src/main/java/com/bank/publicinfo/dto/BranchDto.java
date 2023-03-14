package com.bank.publicinfo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchDto {
    Long id;
    String address;
    Long phoneNumber;
    String city;
    LocalTime startOfWork;
    LocalTime endOfWork;
}
