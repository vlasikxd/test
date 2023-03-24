package com.bank.publicinfo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime startOfWork;
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime endOfWork;
    Boolean allHours;
    BranchDto branch;
}
