package com.bank.publicinfo.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AtmDto implements Serializable {
    Long id;
    String address;
    LocalTime startOfWork;
    LocalTime endOfWork;
    Boolean allHours;
    BranchDto branch;
}
