package com.bank.account.dto.profileDto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationDto {

    Long id;
    String country;
    String region;
    String city;
    String district;
    String locality;
    String street;
    String houseNumber;
    String houseBlock;
    String flatNumber;
    Long index;
}
