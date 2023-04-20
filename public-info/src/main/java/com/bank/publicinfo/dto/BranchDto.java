package com.bank.publicinfo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchDto implements Serializable {
    Long id;
    String address;
    Long phoneNumber;
    String city;
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime startOfWork;
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime endOfWork;
}
